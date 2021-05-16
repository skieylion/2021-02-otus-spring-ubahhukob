package spring.homework.services;

import spring.homework.domain.Book;
import spring.homework.exceptions.BookException;

import java.util.List;

public interface ServiceBook {
    Book read(String id) throws BookException;
    List<Book> readAll();
    void update(String bookId, String newName) throws BookException;
    boolean delete(String bookId);
    String create(String bookName,String authorName,String genreName,String commentValue) throws BookException;
}
