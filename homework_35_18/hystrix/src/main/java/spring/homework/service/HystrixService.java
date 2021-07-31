package spring.homework.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
@AllArgsConstructor
public class HystrixService {

    private final String host="http://localhost:8080";

    @Autowired
    private final RestTemplate restTemplate;

    private ResponseEntity<String> fallback() {
        return ResponseEntity.badRequest().body("Error");
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public ResponseEntity<String> find(String id){
        String response =restTemplate.getForObject(host+"/find/"+id,String.class);
        assert response != null;
        return ResponseEntity.ok(response);
    }
    @HystrixCommand(fallbackMethod = "fallback")
    public ResponseEntity<String> findAll(){
        String response =restTemplate.getForObject(host+"/find",String.class);
        assert response != null;
        return ResponseEntity.ok(response);
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public ResponseEntity<String> delete(String id){
        String response =restTemplate.getForObject(host+"/delete/"+id,String.class);
        assert response != null;
        return ResponseEntity.ok(response);
    }
    @HystrixCommand(fallbackMethod = "fallback")
    public ResponseEntity<String> update(String id,String name){
        String response =restTemplate.getForObject(host+"/update/"+id+"?name="+name,String.class);
        assert response != null;
        return ResponseEntity.ok(response);
    }
    @HystrixCommand(fallbackMethod = "fallback")
    public ResponseEntity<String> create(String name,String author,String genre,String comment){
        String response =restTemplate.getForObject(host+"/create?name="+name+"&author="+author+"&genre="+genre+"&comment="+comment,String.class);
        assert response != null;
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<String> index(){
        String response =restTemplate.getForObject(host,String.class);
        assert response != null;
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<String> path(String path){
        String response =restTemplate.getForObject(host+"/view/"+path,String.class);
        assert response != null;
        return ResponseEntity.ok(response);
    }

}