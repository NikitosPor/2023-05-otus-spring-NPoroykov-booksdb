package ru.otus.booksdb.dao;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.booksdb.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class GenreDaoJDBC implements GenreDao {

    private final JdbcOperations jdbc;

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public GenreDaoJDBC(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.jdbc = namedParameterJdbcOperations.getJdbcOperations();
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public int count() {
        Integer count = jdbc.queryForObject("select count(*) from GENRES", Integer.class);
        return count == null ? 0 : count;
    }

    @Override
    public Genre getByTitle(String title) {
        final Map<String, Object> params = Map.of("GENRE", title);
        var resultOnGenreQuery = namedParameterJdbcOperations
                .query("select ID, GENRE from GENRES where GENRE = :GENRE",
                        params, new GenreDaoJDBC.GenreMapper());

        if (resultOnGenreQuery.isEmpty()) {
            return new Genre(-1, "n/a");
        } else {
            return resultOnGenreQuery.get(0);
        }
    }

    @Override
    public long insert(Genre genre) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("GENRE", genre.getGenre());

        KeyHolder kh = new GeneratedKeyHolder();
        String insertQuery = "insert into GENRES (GENRE) values (:GENRE)";

        namedParameterJdbcOperations.update(insertQuery, params, kh);
        return Objects.requireNonNull(kh.getKey()).longValue();
    }

    @Override
    public Genre getById(long id) {
        final Map<String, Object> params = Map.of("ID", id);
        var resultOnGenreQuery = namedParameterJdbcOperations
                .query("select ID, GENRE from GENRES where ID = :ID",
                        params, new GenreDaoJDBC.GenreMapper());

        if (resultOnGenreQuery.isEmpty()) {
            return new Genre(-1, "n/a");
        } else {
            return resultOnGenreQuery.get(0);
        }
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query("select ID, GENRE from GENRES", new GenreDaoJDBC.GenreMapper());
    }

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("ID");
            String genre = resultSet.getString("GENRE");

            return new Genre(id, genre);
        }
    }

}
