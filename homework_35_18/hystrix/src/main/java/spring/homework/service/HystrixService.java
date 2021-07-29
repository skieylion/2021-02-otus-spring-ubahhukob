package spring.homework.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
@AllArgsConstructor
public class HystrixService {

    @Autowired
    private final RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "callUserService_Fallback")
    public ResponseEntity<String> call(){
        String response =restTemplate.getForObject("http://localhost:8080/book",String.class);
        return ResponseEntity.ok(response);
    }
    private ResponseEntity<String> callUserService_Fallback() {
        return ResponseEntity.badRequest().body("Error");
    }
}