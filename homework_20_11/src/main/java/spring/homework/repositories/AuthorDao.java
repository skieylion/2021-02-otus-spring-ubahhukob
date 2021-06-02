package spring.homework.repositories;

import org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.CrudRepository;
import spring.homework.domain.Author;

public interface AuthorDao extends ReactiveMongoRepository<Author,String> {

}
