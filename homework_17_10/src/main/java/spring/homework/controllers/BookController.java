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

@Controller
@AllArgsConstructor
public class BookController {

    private final ServiceBook serviceBook;

    @GetMapping("/api/find/{id}")
    public String find(@PathVariable("id") String id, Model model) throws BookException {
        model.addAttribute("books",Arrays.asList(serviceBook.read(id)));
        return "books";
    }

    @GetMapping("/api/find")
    public String findAll(@PathParam("id") String id,Model model) throws BookException {
        if(id!=null) return "redirect:/api/find/"+id;
        model.addAttribute("books",serviceBook.readAll());
        return "books";
    }

    @GetMapping("/api/delete")
    public String deleteByParamId(@PathParam("id") String id) throws BookException {
        return "redirect:/api/delete/"+id;
    }
    @GetMapping("/api/delete/{id}")
    public String delete(@PathVariable("id") String id,Model model) {
        if(serviceBook.delete(id)) model.addAttribute("deleted", true);
        else model.addAttribute("deleted", false);
        return "delete";
    }

    @GetMapping("/api/update/{id}")
    public String update(@PathVariable("id") String id,@PathParam("name") String name) throws BookException {
        serviceBook.update(id,name);
        return "redirect:/api/find/"+id;
    }
    @GetMapping("/api/update")
    public String updateByParamName(@PathParam("id") String id,@PathParam("name") String name) {
        return "redirect:/api/update/"+id+"?name="+name;
    }

    @PostMapping("/api/create")
    public String create(@PathParam("name") String name,@PathParam("author") String author, @PathParam("genre") String genre,@PathParam("comment") String comment) throws BookException {
        String id = serviceBook.create(name,author,genre,comment);
        return "redirect:/api/find/"+id;
    }
}