package spring.homework.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.homework.exceptions.BookException;
import spring.homework.services.ServiceBook;

import java.util.Arrays;

@Controller
@AllArgsConstructor
public class BookController {

    private final ServiceBook serviceBook;

    @GetMapping("/find/{id}")
    public String find(@PathVariable("id") String id, Model model) throws BookException {
        model.addAttribute("books",Arrays.asList(serviceBook.read(id)));
        return "books";
    }

    @GetMapping("/find")
    public String findAll(String id, Model model) throws BookException {
        System.out.println("find_all");
        if(id!=null) return "redirect:/find/"+id;
        model.addAttribute("books",serviceBook.readAll());
        model.addAttribute("isFindAll",true);
        return "books";
    }
    //@RequestParam(name="id",defaultValue = "")

    @PostMapping("/delete")
    public String deleteByParamId(@RequestParam("id") String id) throws BookException {
        return "redirect:/delete/"+id;
    }
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id,Model model) {
        if(serviceBook.delete(id)) model.addAttribute("deleted", true);
        else model.addAttribute("deleted", false);
        return "delete";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") String id,@RequestParam("name") String name) throws BookException {
        serviceBook.update(id,name);
        return "redirect:/find/"+id;
    }
    @GetMapping("/update")
    public String updateByParamName(@RequestParam("id") String id,@RequestParam("name") String name) {
        return "redirect:/update/"+id+"?name="+name;
    }

    @PostMapping("/create")
    public String create(@RequestParam("name") String name,@RequestParam("author") String author, @RequestParam("genre") String genre,@RequestParam("comment") String comment) throws BookException {
        String id = serviceBook.create(name,author,genre,comment);
        return "redirect:/find/"+id;
    }
}