package spring.homework.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import spring.homework.model.BookVO;

import java.net.URI;
import java.util.Date;

@Service
@AllArgsConstructor
public class HystrixService {

    private final String host="http://localhost:8080";

    @Autowired
    private final RestTemplate restTemplate;

    private ResponseEntity<String> fallbackFindAll() {
        return ResponseEntity.badRequest().body("Error");
    }
    private ResponseEntity<String> fallbackCreate(BookVO book) {
        return ResponseEntity.badRequest().body("Error book");
    }
    private ResponseEntity<String> fallbackFind(String id) {
        return ResponseEntity.badRequest().body("Error find by id="+id);
    }
    private ResponseEntity<String> fallbackDelete(String id) {
        return ResponseEntity.badRequest().body("Error delete by id="+id);
    }
    private ResponseEntity<String> fallbackUpdate(BookVO book) {
        return ResponseEntity.badRequest().body("Error update");
    }

    @HystrixCommand(fallbackMethod = "fallbackFind")
    public ResponseEntity<String> find(String id){
        String response =restTemplate.getForObject(host+"/book/"+id,String.class);
        assert response != null;
        return ResponseEntity.ok(response);
    }
    @HystrixCommand(fallbackMethod = "fallbackFindAll")
    public ResponseEntity<String> findAll(){
        String response =restTemplate.getForObject(host+"/book",String.class);
        assert response != null;
        return ResponseEntity.ok(response);
    }

    @HystrixCommand(fallbackMethod = "fallbackDelete")
    public ResponseEntity<String> delete(String id){
        restTemplate.delete(host+"/book/"+id);
        return ResponseEntity.ok().build();
    }
    @HystrixCommand(fallbackMethod = "fallbackUpdate")
    public ResponseEntity<String> update(BookVO book){
        restTemplate.put(host+"/book",book);
        return ResponseEntity.ok().build();
    }

    @HystrixCommand(fallbackMethod = "fallbackCreate")
    public ResponseEntity<String> create(BookVO book) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String  bookAsString = objectMapper.writeValueAsString(book);
        System.out.println(bookAsString);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(bookAsString, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(host+"/book",entity,String.class);
        System.out.println(response);
        return response;
    }

    public ResponseEntity<String> index(){
        String response =restTemplate.getForObject(host,String.class);
        assert response != null;
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<String> path(String path){
        String response =restTemplate.getForObject(host+"/"+path,String.class);
        assert response != null;
        return ResponseEntity.ok(response);
    }

}