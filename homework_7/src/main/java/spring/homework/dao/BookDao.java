package spring.homework.dao;

import spring.homework.domain.Book;

import java.util.List;

public interface BookDao {
    long create(Book book);
    Book read(long id);
    void update(Book book);
    void delete(long id);
    List<Book> readAll();
}
