package spring.homework.controllers;

import lombok.AllArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spring.homework.domain.Book;
import spring.homework.repositories.BookDao;

import java.util.concurrent.Flow;

@RestController
@AllArgsConstructor
public class BookController {
    private final BookDao bookDao;

    @GetMapping("/book/{id}")
    public Mono<Book> find(@PathVariable("id") String id) {
        return bookDao.findById(id);
    }
    @GetMapping("/book")
    public Flux<Book> findAll() {
        return bookDao.findAll();
    }
    @DeleteMapping("/book/{id}")
    public Mono<Void> delete(@PathVariable("id") String id) {
        return bookDao.deleteById(id);
    }
    @PostMapping("/book")
    public Mono<Book> create(@RequestBody Book book) {
        return bookDao.save(book);
    }
    @PutMapping("/book")
    public Mono<Book> update(@RequestBody Book book) {
        return bookDao.save(book);
    }
}