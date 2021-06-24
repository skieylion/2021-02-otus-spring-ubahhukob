package spring.homework.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.homework.domain.Genre;

public interface GenreRepository extends CrudRepository<Genre,String> {
}
