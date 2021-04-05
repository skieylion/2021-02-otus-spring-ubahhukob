package spring.homework.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.homework.domain.Author;
import spring.homework.domain.Book;
import spring.homework.domain.Comment;
import spring.homework.domain.Genre;
import spring.homework.repositories.AuthorDao;
import spring.homework.repositories.BookDao;
import spring.homework.repositories.GenreDao;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceBookImpl implements ServiceBook{
    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    public ServiceBookImpl(BookDao bookDao, AuthorDao authorDao, GenreDao genreDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @Override
    public String read(long id) {
        return bookDao.read(id).toString();
    }

    @Override
    public String readAll() {
        List<Book> bookList=bookDao.readAll();
        return bookList.toString();
    }

    @Override
    @Transactional
    public String update(long bookId, String newName) {
        Book book=bookDao.read(bookId);
        book.setName(newName);
        bookDao.save(book);
        return bookDao.read(bookId).toString();
    }

    @Override
    @Transactional
    public String delete(long bookId) {
        bookDao.delete(bookId);
        return "deleted the book";
    }

    @Override
    @Transactional
    public String create(String bookName, String authorName, String genreName, String commentValue) {
        Author author=new Author(authorName,"");
        Genre genre=new Genre(genreName);
        Comment comment=new Comment(commentValue);
        author.setId(authorDao.save(author));
        genre.setId(genreDao.save(genre));
        List<Comment> commentList=new ArrayList<>();
        commentList.add(comment);
        Book book=new Book(bookName,author,genre,commentList);
        long id=bookDao.save(book);
        return bookDao.read(id).toString();
    }
}
