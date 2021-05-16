package spring.homework.services;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.homework.domain.Author;
import spring.homework.domain.Book;
import spring.homework.domain.Comment;
import spring.homework.domain.Genre;
import spring.homework.exceptions.BookException;
import spring.homework.repositories.AuthorDao;
import spring.homework.repositories.BookDao;
import spring.homework.repositories.CommentDao;
import spring.homework.repositories.GenreDao;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceBookImpl implements ServiceBook{
    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final CommentDao commentDao;
    private final ServiceStringBook serviceStringBook;

    public ServiceBookImpl(BookDao bookDao, AuthorDao authorDao, GenreDao genreDao, CommentDao commentDao, ServiceStringBook serviceStringBook) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
        this.commentDao = commentDao;
        this.serviceStringBook = serviceStringBook;
    }

    @Transactional(readOnly = true)
    @Override
    public Book read(String id) throws BookException {
        return bookDao.findById(id).orElseThrow(BookException::new);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> readAll() {
        List<Book> bookList=bookDao.findAll();
        return bookList;
    }

    @Override
    @Transactional
    public void update(String bookId, String newName) throws BookException {
        Book book=bookDao.findById(bookId).orElseThrow(BookException::new);
        book.setName(newName);
        bookDao.save(book);
    }

    @Override
    @Transactional
    public void delete(String bookId) {
	    commentDao.deleteByBookId(bookId);
        if(bookDao.existsById(bookId)) {
            bookDao.deleteById(bookId);
        }
    }

    @Override
    @Transactional
    public String create(Book book) throws BookException {

        book.getComments().forEach(comment -> {
            comment.setId(new ObjectId().toHexString());
        });

        book=bookDao.save(book);
        commentDao.saveAll(book.getComments());

        String id=book.getId();
        return id;
    }

}
