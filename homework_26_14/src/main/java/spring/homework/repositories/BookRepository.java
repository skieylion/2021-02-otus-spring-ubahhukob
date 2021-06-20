package spring.homework.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import spring.homework.domain.Book;

import java.util.List;

public interface BookRepository extends MongoRepository<Book,String> {
    List<Book> findAll();
}
