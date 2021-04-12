package spring.homework.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import spring.homework.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;

@Repository
public class AuthorDaoImpl implements AuthorDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public AuthorDaoImpl(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public Author read(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        Author author = namedParameterJdbcOperations.queryForObject(
                "select id,fullname,alias from AUTHORS where id = :id", params, new AuthorDaoImpl.AuthorMapper()
        );
        return author;
    }

    @Override
    public long save(Author author) {
        if (author.getId() != 0) {
            Map<String, Object> params = Map.of(
                    "id", author.getId(),
                    "fullname", author.getFullName(),
                    "alias", author.getAlias()
            );

            namedParameterJdbcOperations.update(
                    "update AUTHORS set fullname=:fullname, alias=:alias where id = :id",
                    params
            );
        } else {
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("fullname", author.getFullName());
            params.addValue("alias", author.getAlias());
            KeyHolder kh = new GeneratedKeyHolder();
            namedParameterJdbcOperations.update(
                    "insert into AUTHORS(fullname,alias) values(:fullname,:alias)",
                    params,
                    kh
            );
            return kh.getKey().longValue();
        }

        return 0;
    }

    @Override
    public void delete(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "delete from AUTHORS where id = :id", params
        );
    }

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("fullname");
            String alias = resultSet.getString("alias");

            return new Author(id, name, alias);
        }
    }
}
