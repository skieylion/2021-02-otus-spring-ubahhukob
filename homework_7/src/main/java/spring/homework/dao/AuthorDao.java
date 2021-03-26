package spring.homework.dao;

import spring.homework.domain.Author;
import spring.homework.domain.Book;

public interface AuthorDao {
    Author read(long id);
    long create(Author author);
    void update(Author author);
    void delete(long id);
}
