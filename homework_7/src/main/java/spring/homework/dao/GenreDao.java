package spring.homework.dao;

import spring.homework.domain.Author;
import spring.homework.domain.Book;
import spring.homework.domain.Genre;

public interface GenreDao {
    long save(Genre genre);

    Genre read(long id);

    void delete(long id);
}
