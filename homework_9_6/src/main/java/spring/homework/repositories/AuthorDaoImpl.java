package spring.homework.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.homework.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class AuthorDaoImpl implements AuthorDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Author read(long id) {
        Author author = em.find(Author.class, id);
        return author;
    }

    private void update(Author author) {
        em.merge(author);
    }

    private long create(Author author) {
        em.persist(author);
        return author.getId();
    }

    @Override
    @Transactional
    public long save(Author author) {
        long id = author.getId();
        if (id != 0) {
            update(author);
        } else {
            return create(author);
        }
        return id;
    }

    @Override
    @Transactional
    public void delete(long id) {
        Author author = em.merge(new Author(id));
        em.remove(author);
    }
}
