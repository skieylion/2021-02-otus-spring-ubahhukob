package spring.homework.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import spring.homework.domain.Author;

public interface AuthorDao extends MongoRepository<Author,String> {

}
