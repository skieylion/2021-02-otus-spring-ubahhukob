package spring.homework.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import spring.homework.domain.Author;
import spring.homework.domain.Genre;

import java.util.List;

public interface GenreRepository extends MongoRepository<Genre,String> {

}
