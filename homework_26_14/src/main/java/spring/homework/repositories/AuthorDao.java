package spring.homework.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import spring.homework.domain.Author;

import java.util.List;

public interface AuthorDao extends MongoRepository<Author,String> {
    List<Author> findByFullName(String fullName);
}
