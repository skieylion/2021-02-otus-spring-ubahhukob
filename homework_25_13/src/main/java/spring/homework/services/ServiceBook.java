package spring.homework.services;

import spring.homework.domain.Book;
import spring.homework.exceptions.BookException;
import java.util.List;

public interface ServiceBook {
    Book read(long id) throws BookException;
    List<Book> readAll();
    void update(long bookId, String newName) throws BookException;
    void delete(long bookId);
    long create(Book book) throws BookException;
}
