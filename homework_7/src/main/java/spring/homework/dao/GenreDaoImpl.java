package spring.homework.dao;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import spring.homework.domain.Author;
import spring.homework.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;

@Repository
public class GenreDaoImpl extends DefaultDaoImpl implements GenreDao {

    public GenreDaoImpl(JdbcOperations jdbc, NamedParameterJdbcOperations namedParameterJdbcOperations) {
        super(jdbc, namedParameterJdbcOperations);
    }

    @Override
    public Genre read(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        Genre genre=namedParameterJdbcOperations.queryForObject(
                "select id,name from GENRES where id = :id", params,new GenreDaoImpl.GenreMapper()
        );
        return genre;
    }
    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");

            return new Genre(id, name);
        }
    }
}
