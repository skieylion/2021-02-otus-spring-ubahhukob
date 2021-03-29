package spring.homework.repositories;

import spring.homework.domain.Author;

public interface AuthorDao {
    Author read(long id);

    long save(Author author);

    void delete(long id);
}
