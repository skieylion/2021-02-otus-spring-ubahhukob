package spring.homework.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.homework.domain.Book;
import spring.homework.exceptions.BookException;
import spring.homework.services.ServiceBook;

import java.util.List;

@RestController
@AllArgsConstructor
public class BookController {

    private final ServiceBook serviceBook;

    @GetMapping("/book/{id}")
    public Book find(@PathVariable("id") long id) throws BookException {
        return serviceBook.read(id);
    }

    @GetMapping("/book")
    public List<Book> findAll() throws BookException {
        return serviceBook.readAll();
    }

    @DeleteMapping("/book/{id}")
    public void delete(@PathVariable("id") long id) {
        serviceBook.delete(id);
    }

    @PutMapping("/book")
    public void update(@RequestBody Book book) throws BookException {
        serviceBook.update(book.getId(),book.getName());
    }

    @PostMapping("/book")
    public long create(@RequestBody Book book) throws BookException {
        return serviceBook.create(book);
    }
}