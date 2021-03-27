package spring.homework.dao;

import spring.homework.domain.Author;

public interface AuthorDao {
    Author read(long id);
    long create(Author author);
    void update(Author author);
    void delete(long id);
}
