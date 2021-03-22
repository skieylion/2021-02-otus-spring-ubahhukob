package spring.homework.dao;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import spring.homework.domain.Author;
import spring.homework.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;

@Repository
public class AuthorDaoImpl extends DefaultDaoImpl implements AuthorDao {
    public AuthorDaoImpl(JdbcOperations jdbc, NamedParameterJdbcOperations namedParameterJdbcOperations) {
        super(jdbc, namedParameterJdbcOperations);
    }

    @Override
    public Author read(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        Author author=namedParameterJdbcOperations.queryForObject(
                "select id,fullname,alias from AUTHORS where id = :id", params,new AuthorDaoImpl.AuthorMapper()
        );
        return author;
    }
    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("fullname");
            String alias=resultSet.getString("alias");

            return new Author(id, name,alias);
        }
    }
}
