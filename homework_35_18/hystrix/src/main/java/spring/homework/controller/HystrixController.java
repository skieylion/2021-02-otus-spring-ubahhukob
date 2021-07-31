package spring.homework.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import spring.homework.service.HystrixService;

import java.util.Arrays;

@RestController
@AllArgsConstructor
public class HystrixController {


    @Autowired
    private final HystrixService hystrixService;

    @GetMapping("/find/{id}")
    public ResponseEntity<String> find(@PathVariable("id") String id)  {
        return hystrixService.find(id);
    }

    @GetMapping("/find")
    public ResponseEntity<String> findAll(String id, Model model){
        return hystrixService.findAll();
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") String id) {
        return hystrixService.delete(id);
    }

    @GetMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable("id") String id,@RequestParam("name") String name) {
        return hystrixService.update(id,name);
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestParam("name") String name,@RequestParam("author") String author, @RequestParam("genre") String genre,@RequestParam("comment") String comment) {
        return hystrixService.create(name,author,genre,comment);
    }
    @GetMapping("/")
    public ResponseEntity<String> index() {
        return hystrixService.index();
    }

    @GetMapping("/view/find")
    public ResponseEntity<String> find(){
        return hystrixService.path("find");
    }
    @GetMapping("/view/find-all")
    public ResponseEntity<String> findAll(){
        return hystrixService.path("find-all");
    }
    @GetMapping("/view/update")
    public ResponseEntity<String> update(){
        return hystrixService.path("update");
    }
    @GetMapping("/view/create")
    public ResponseEntity<String> create(){
        return hystrixService.path("create");
    }
    @GetMapping("/view/delete")
    public ResponseEntity<String> delete(){
        return hystrixService.path("delete");
    }

}
