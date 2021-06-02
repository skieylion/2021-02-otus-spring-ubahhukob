package spring.homework.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spring.homework.domain.Book;

import java.util.List;

public interface BookDao extends ReactiveMongoRepository<Book,String> {
    Flux<Book> findAll();
    Mono<Book> save(Mono<Book> book);
}
