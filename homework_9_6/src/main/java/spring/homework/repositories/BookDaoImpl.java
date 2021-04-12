package spring.homework.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.homework.domain.Author;
import spring.homework.domain.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;


@Repository
public class BookDaoImpl implements BookDao {

    @PersistenceContext
    private EntityManager em;

    public BookDaoImpl() {
    }
    private void update(Book book) {
        em.merge(book);
    }
    private long create(Book book) {
        em.persist(book);
        return book.getId();
    }


    @Override
    public long save(Book book) {
        long id = book.getId();
        if (id != 0) {
            update(book);
        } else {
            return create(book);
        }
        return id;
    }

    @Override
    public void delete(long id) {
        Query query = em.createQuery("delete Book e where e.id=:id");
        query.setParameter("id",id);
        query.executeUpdate();
    }


    @Override
    public Book read(long id) {
        Book book = em.find(Book.class, id);
        return book;
    }

    @Override
    public List<Book> readAll() {
        List<Book> books = em.createQuery("select e from Book e join fetch e.author join fetch e.genre ", Book.class).getResultList();
        return books;
    }
}