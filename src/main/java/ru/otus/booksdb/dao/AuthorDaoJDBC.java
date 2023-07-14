package ru.otus.booksdb.dao;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.booksdb.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class AuthorDaoJDBC implements AuthorDao {

    private final JdbcOperations jdbc;

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public AuthorDaoJDBC(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.jdbc = namedParameterJdbcOperations.getJdbcOperations();
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public int count() {
        Integer count = jdbc.queryForObject("select count(*) from AUTHORS", Integer.class);
        return count == null ? 0 : count;
    }

    @Override
    public long insert(Author author) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("AUTHOR", author.getAuthor());

        KeyHolder kh = new GeneratedKeyHolder();
        String insertQuery = "insert into AUTHORS (AUTHOR) values (:AUTHOR)";

        namedParameterJdbcOperations.update(insertQuery, params, kh);
        return Objects.requireNonNull(kh.getKey()).longValue();
    }

    @Override
    public Author getById(long id) {
        final Map<String, Object> params = Map.of("ID", id);
        List<Author> resultOnAuthorQuery = namedParameterJdbcOperations
                .query("select ID, AUTHOR from AUTHORS where ID = :ID", params, new AuthorDaoJDBC.AuthorMapper());
        if (resultOnAuthorQuery.isEmpty()) {
            return new Author(-1, "n/a");
        } else {
            return resultOnAuthorQuery.get(0);
        }
    }

    @Override
    public Author getByName(String name) {
        final Map<String, Object> params = Map.of("AUTHOR", name);
        var resultOnAuthorQuery = namedParameterJdbcOperations
                .query("select ID, AUTHOR from AUTHORS where AUTHOR = :AUTHOR",
                        params, new AuthorDaoJDBC.AuthorMapper());

        if (resultOnAuthorQuery.isEmpty()) {
            return new Author(-1, "n/a");
        } else {
            return resultOnAuthorQuery.get(0);
        }
    }

    @Override
    public List<Author> getAll() {
        return jdbc.query("select ID, AUTHOR from AUTHORS", new AuthorDaoJDBC.AuthorMapper());
    }

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("ID");
            String author = resultSet.getString("AUTHOR");
            return new Author(id, author);
        }
    }

}
