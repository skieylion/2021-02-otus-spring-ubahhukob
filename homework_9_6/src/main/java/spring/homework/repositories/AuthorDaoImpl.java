package spring.homework.repositories;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.homework.domain.Author;
import spring.homework.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;

@Repository
public class AuthorDaoImpl implements AuthorDao {

    @PersistenceContext
    private EntityManager em;

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public AuthorDaoImpl(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public Author read(long id) {
        Author author=em.find(Author.class,id);
        return author;
    }

    @Override
    @Transactional
    public long save(Author author) {
        if(author.getId()!=0){
            em.merge(author);
        } else {
            em.persist(author);
            return author.getId();
        }

        return 0;
    }

    @Override
    @Transactional
    public void delete(long id) {
        Author author=em.merge(new Author(id));
        em.remove(author);
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
