package spring.homework.repositories;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.homework.domain.Book;
import spring.homework.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;

@Repository
public class GenreDaoImpl implements GenreDao {
    @PersistenceContext
    private EntityManager em;

    private void update(Genre genre) {
        em.merge(genre);
    }

    private long create(Genre genre) {
        em.persist(genre);
        return genre.getId();
    }

    @Override
    public long save(Genre genre) {
        long id = genre.getId();
        if (id != 0) {
            update(genre);
        } else {
            return create(genre);
        }
        return id;
    }

    @Override
    public Genre read(long id) {
        Genre genre = em.find(Genre.class, id);
        return genre;
    }

    @Override
    public void delete(long id) {
        Query query = em.createQuery("delete Genre e where e.id=:id");
        query.setParameter("id",id);
        query.executeUpdate();
    }
}
