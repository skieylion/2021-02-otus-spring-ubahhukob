package spring.homework.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import spring.homework.model.BookVO;
import spring.homework.service.HystrixService;

import java.util.Arrays;

@RestController
@AllArgsConstructor
public class HystrixController {


    @Autowired
    private final HystrixService hystrixService;

    @GetMapping("/book/{id}")
    public ResponseEntity<String> find(@PathVariable("id") String id)  {
        return hystrixService.find(id);
    }

    @GetMapping("/book")
    public ResponseEntity<String> findAll(String id, Model model){
        return hystrixService.findAll();
    }

    @DeleteMapping("/book/{id}")
    public void delete(@PathVariable("id") String id) {
        hystrixService.delete(id);
    }

    @PutMapping("/book")
    public void update(@RequestBody BookVO book) {
        hystrixService.update(book);
    }

    @PostMapping("/book")
    public ResponseEntity<String> create(@RequestBody BookVO book) throws JsonProcessingException {
        return hystrixService.create(book);
    }
    @GetMapping("/")
    public ResponseEntity<String> index() {
        return hystrixService.index();
    }

    @GetMapping("/table")
    public ResponseEntity<String> find(){
        return hystrixService.path("table.js");
    }
    @GetMapping("/actions")
    public ResponseEntity<String> findAll(){
        return hystrixService.path("actions.js");
    }

}
