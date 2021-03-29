package spring.homework.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.homework.domain.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    @Override
    @Transactional
    public long save(Book book) {
        authorDao.save(book.getAuthor());
        genreDao.save(book.getGenre());
        commentDao.save(book.getComment());
        if (book.getId() != 0) {
            em.merge(book);
        } else {
            em.persist(book);
            return book.getId();
        }
        return 0;
    }

    @Override
    @Transactional
    public void delete(long id) {
        Book book = em.merge(new Book(id));
        em.remove(book);
    }


    @Override
    public Book read(long id) {
        Book book = em.find(Book.class, id);
        return book;
    }

    @Override
    public List<Book> readAll() {
        List<Book> books = em.createQuery("select e from BOOKS e", Book.class).getResultList();
        return books;
    }
}