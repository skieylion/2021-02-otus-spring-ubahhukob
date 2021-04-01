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

    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final CommentDao commentDao;

    public BookDaoImpl(AuthorDao authorDao, GenreDao genreDao, CommentDao commentDao) {
        this.authorDao = authorDao;
        this.genreDao = genreDao;
        this.commentDao = commentDao;
    }
    @Transactional
    private void update(Book book) {
        em.merge(book);
    }
    @Transactional
    private long create(Book book) {
        em.persist(book);
        return book.getId();
    }


    @Override
    public long save(Book book) {
        authorDao.save(book.getAuthor());
        genreDao.save(book.getGenre());
        book.getComments().forEach(comment -> commentDao.save(comment));
        long id = book.getId();
        if (id != 0) {
            update(book);
        } else {
            return create(book);
        }
        return id;
    }

    @Override
    @Transactional
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
        List<Book> books = em.createQuery("select e from Book e join fetch e.author join fetch e.genre", Book.class).getResultList();
        return books;
    }
}