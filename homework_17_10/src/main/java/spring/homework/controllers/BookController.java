package spring.homework.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import spring.homework.domain.Book;
import spring.homework.exceptions.BookException;
import spring.homework.repositories.BookDao;
import spring.homework.services.ServiceBook;

import javax.websocket.server.PathParam;
import java.util.Arrays;
import java.util.List;

@RestController
@AllArgsConstructor
public class BookController {

    private final ServiceBook serviceBook;

    @GetMapping("/api/find/{id}")
    public Book find(@PathVariable("id") String id) throws BookException {
        return serviceBook.read(id);
    }

    @GetMapping("/api/find")
    public List<Book> findAll() throws BookException {
        return serviceBook.readAll();
    }

    @DeleteMapping("/api/delete/{id}")
    public void delete(@PathVariable("id") String id) {
        serviceBook.delete(id);
    }

    @PutMapping("/api/update/{id}")
    public void update(@PathVariable("id") String id,@PathParam("name") String name) throws BookException {
        serviceBook.update(id,name);
    }

    @PostMapping("/api/create")
    public String create(@PathParam("name") String name,@PathParam("author") String author, @PathParam("genre") String genre,@PathParam("comment") String comment) throws BookException {
        String id = serviceBook.create(name,author,genre,comment);
        return id;
    }
}