package ru.otus.booksdb.dao;


import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.booksdb.domain.Author;
import ru.otus.booksdb.domain.Book;
import ru.otus.booksdb.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class BookDaoJDBC implements BookDao {

    private final JdbcOperations jdbc;

    private final AuthorDao authorDao;

    private final GenreDao genreDao;

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public BookDaoJDBC(NamedParameterJdbcOperations namedParameterJdbcOperations,
                       AuthorDao authorDao,
                       GenreDao genreDao) {
        this.jdbc = namedParameterJdbcOperations.getJdbcOperations();
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @Override
    public int count() {
        Integer count = jdbc.queryForObject("select count(*) from BOOKS", Integer.class);
        return count == null ? 0 : count;
    }

    @Override
    public long insert(Book book) {
        long localAuthorId;
        long localGenreId;
        Author localAuthor = authorDao.getByName(book.getAuthor());
        Genre localGenre = genreDao.getByTitle(book.getGenre());
        if (localAuthor.getId() < 1) {
            localAuthorId = authorDao.insert(new Author(1, book.getAuthor()));
        } else {
            localAuthorId = localAuthor.getId();
        }
        if (localGenre.getId() < 1) {
            localGenreId = genreDao.insert(new Genre(1, book.getGenre()));
        } else {
            localGenreId = localGenre.getId();
        }
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("TITLE", book.getTitle());
        params.addValue("AUTHOR", localAuthorId);
        params.addValue("GENRE", localGenreId);
        KeyHolder kh = new GeneratedKeyHolder();
        String insertQuery = "insert into BOOKS (TITLE, AUTHOR, GENRE) values (:TITLE, :AUTHOR, :GENRE)";
        namedParameterJdbcOperations.update(insertQuery, params, kh);

        return Objects.requireNonNull(kh.getKey()).longValue();
    }

    @Override
    public Book getById(long id) {
        final Map<String, Object> params = Map.of("ID", id);
        var resultOnBookQuery = namedParameterJdbcOperations
                .query("select B.ID, B.TITLE, A.AUTHOR, G.GENRE " +
                                "from BOOKS B left join AUTHORS A on B.AUTHOR = A.ID " +
                                "left join GENRES G on B.GENRE = G.ID WHERE B.ID = :ID",
                        params, new BookMapper());

        if (resultOnBookQuery.isEmpty()) {
            return new Book(-1, "n/a", "n/a", "n/a");
        } else {
            return resultOnBookQuery.get(0);
        }
    }

    @Override
    public Book getByTitle(String title) {
        final Map<String, Object> params = Map.of("TITLE", title);
        return namedParameterJdbcOperations
                .queryForObject("select ID, TITLE, AUTHOR, GENRE from BOOKS where TITLE = :TITLE",
                        params, new BookDaoJDBC.BookMapper());
    }

    @Override
    public void updateTitleById(long id, String newTitle) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("ID", id);
        params.addValue("NEW_TITLE", newTitle);

        namedParameterJdbcOperations.update("update BOOKS B set B.TITLE = :NEW_TITLE WHERE B.ID = :ID", params);
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("select B.ID, B.TITLE, A.AUTHOR, G.GENRE " +
                "from BOOKS B left join AUTHORS A on B.AUTHOR = A.ID " +
                "left join GENRES G on B.GENRE = G.ID", new BookMapper());
    }

    @Override
    public void deleteById(long id) {
        final Map<String, Object> params = Map.of("ID", id);
        namedParameterJdbcOperations.update("delete from BOOKS where ID = :ID", params);
    }


    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("ID");
            String title = resultSet.getString("TITLE");
            String authorId = resultSet.getString("AUTHOR");
            String genreId = resultSet.getString("GENRE");

            return new Book(id, title, authorId, genreId);
        }
    }
}
