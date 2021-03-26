package spring.homework.dao;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import spring.homework.domain.Author;
import spring.homework.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;

@Repository
public class GenreDaoImpl implements GenreDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public GenreDaoImpl(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public Genre read(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        Genre genre=namedParameterJdbcOperations.queryForObject(
                "select id,name from GENRES where id = :id", params,new GenreDaoImpl.GenreMapper()
        );
        return genre;
    }

    @Override
    public long create(Genre genre) {
        MapSqlParameterSource params=new MapSqlParameterSource();
        params.addValue("name",genre.getName());
        KeyHolder kh=new GeneratedKeyHolder();
        namedParameterJdbcOperations.update("insert into GENRES(name) values(:name)",params,kh);

        return kh.getKey().longValue();
    }

    @Override
    public void update(Genre genre) {
        Map<String, Object> params = Map.of(
                "id",genre.getId(),
                "name",genre.getName()
        );

        namedParameterJdbcOperations.update(
                "update GENRES set name=:name where id = :id", params
        );
    }

    @Override
    public void delete(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "delete from GENRES where id = :id", params
        );
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
