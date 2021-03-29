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

    @Override
    @Transactional
    public long save(Author author) {
        if (author.getId() != 0) {
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
        Author author = em.merge(new Author(id));
        em.remove(author);
    }
}
