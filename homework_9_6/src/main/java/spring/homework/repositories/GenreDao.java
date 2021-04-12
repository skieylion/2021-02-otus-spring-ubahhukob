package spring.homework.repositories;

import spring.homework.domain.Genre;

public interface GenreDao {
    long save(Genre genre);

    Genre read(long id);

    void delete(long id);
}
