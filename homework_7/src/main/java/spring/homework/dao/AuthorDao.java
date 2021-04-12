package spring.homework.dao;

import spring.homework.domain.Author;
import spring.homework.domain.Book;

public interface AuthorDao {
    Author read(long id);

    long save(Author author);

    void delete(long id);
}
