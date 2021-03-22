package spring.homework.dao;

import spring.homework.domain.Book;

public interface BookDao {
    long create(Book book);
    Book read(long id);
    void update(Book book);
    void delete(long id);
}
