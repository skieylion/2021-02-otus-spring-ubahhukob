package spring.homework.dao;

import spring.homework.domain.Author;
import spring.homework.domain.Book;

import java.util.List;

public interface BookDao {
    long save(Book book);

    Book read(long id);

    void delete(long id);

    List<Book> readAll();
}
