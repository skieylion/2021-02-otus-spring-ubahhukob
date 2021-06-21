package spring.homework.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import spring.homework.domain.Genre;

public interface GenreDao extends MongoRepository<Genre,String> {
}
