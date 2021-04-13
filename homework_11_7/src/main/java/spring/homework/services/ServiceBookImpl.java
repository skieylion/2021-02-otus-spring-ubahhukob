package spring.homework.services;

import com.google.common.collect.Lists;
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
    private final ServiceStringBook serviceStringBook;

    public ServiceBookImpl(BookDao bookDao, AuthorDao authorDao, GenreDao genreDao, ServiceStringBook serviceStringBook) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
        this.serviceStringBook = serviceStringBook;
    }

    @Transactional(readOnly = true)
    @Override
    public String read(long id) {
        return serviceStringBook.convert(bookDao.findById(id).get());
    }

    @Transactional(readOnly = true)
    @Override
    public String readAll() {
        List<Book> bookList=Lists.newArrayList(bookDao.findAll().iterator());
        return serviceStringBook.convert(bookList);
    }

    @Override
    @Transactional
    public String update(long bookId, String newName) {
        Book book=bookDao.findById(bookId).get();
        book.setName(newName);
        bookDao.save(book);
        return serviceStringBook.convert(bookDao.findById(bookId).get());
    }

    @Override
    @Transactional
    public String delete(long bookId) {
        bookDao.deleteById(bookId);
        return "deleted the book";
    }

    @Override
    @Transactional
    public String create(String bookName, String authorName, String genreName, String commentValue) {
        Author author=new Author(authorName,"");
        Genre genre=new Genre(genreName);
        Comment comment=new Comment(commentValue);
        author.setId(authorDao.save(author).getId());
        genre.setId(genreDao.save(genre).getId());
        List<Comment> commentList=new ArrayList<>();
        commentList.add(comment);
        Book book=new Book(bookName,author,genre,commentList);
        comment.setBook(book);
        long id=bookDao.save(book).getId();
        return serviceStringBook.convert(bookDao.findById(id).get());
    }
}
