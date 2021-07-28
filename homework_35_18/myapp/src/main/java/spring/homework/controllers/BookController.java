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

    @GetMapping("/book")
    public List<Book> findAll() throws BookException {
        return serviceBook.readAll();
    }
}