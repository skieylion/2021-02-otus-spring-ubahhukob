package spring.homework.repositories;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.homework.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;

@Repository
public class GenreDaoImpl implements GenreDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public long save(Genre genre) {
        if(genre.getId()!=0){
            em.merge(genre);
        } else {
            em.persist(genre);
            return genre.getId();
        }
        return 0;
    }

    @Override
    public Genre read(long id) {
        Genre genre=em.find(Genre.class,id);
        return genre;
    }

    @Override
    @Transactional
    public void delete(long id) {
        Genre genre=em.merge(new Genre(id));
        em.remove(genre);
    }
}
