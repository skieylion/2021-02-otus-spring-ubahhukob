package spring.homework.dao;

import spring.homework.domain.Genre;

public interface GenreDao {
    Genre read(long id);
    long create(Genre genre);
    void update(Genre genre);
    void delete(long id);
}
