package spring.homework.services;

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
    public String read(String id) throws BookException {
        Book book=bookDao.findById(id).orElseThrow(BookException::new);
        return serviceStringBook.convert(book);
    }

    @Transactional(readOnly = true)
    @Override
    public String readAll() {
        List<Book> bookList=bookDao.findAll();
        return serviceStringBook.convert(bookList);
    }

    @Override
    @Transactional
    public String update(String bookId, String newName) throws BookException {
        Book book=bookDao.findById(bookId).orElseThrow(BookException::new);
        book.setName(newName);
        bookDao.save(book);
        return serviceStringBook.convert(bookDao.findById(bookId).orElseThrow(BookException::new));
    }

    @Override
    @Transactional
    public String delete(String bookId) throws BookException {
        Book book=bookDao.findById(bookId).orElseThrow(BookException::new);
        commentDao.deleteAll(book.getComments());
        bookDao.deleteById(book.getId());
        return "deleted the book with comments";
    }

    @Override
    @Transactional
    public String create(String bookName, String authorName, String genreName, String commentValue) throws BookException {
        Author author=new Author(authorName,"");
        Genre genre=new Genre(genreName);
        Comment comment=new Comment(commentValue);
        author.setId(authorDao.save(author).getId());
        genre.setId(genreDao.save(genre).getId());
        List<Comment> commentList=new ArrayList<>();
        commentList.add(comment);
        commentDao.saveAll(commentList);
        Book book=new Book(bookName,author,genre,commentList);
        //comment.setBook(book);

        String id=bookDao.save(book).getId();
        return serviceStringBook.convert(bookDao.findById(id).orElseThrow(BookException::new));
    }

}
