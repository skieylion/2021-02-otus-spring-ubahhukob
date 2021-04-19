package spring.homework.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.homework.domain.Genre;

public interface GenreDao extends CrudRepository<Genre,Long> {
}
