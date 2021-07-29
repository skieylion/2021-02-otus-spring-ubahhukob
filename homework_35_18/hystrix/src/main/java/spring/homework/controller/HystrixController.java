package spring.homework.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import spring.homework.service.HystrixService;

@RestController
@AllArgsConstructor
public class HystrixController {

    @Autowired
    private final HystrixService hystrixService;

    @GetMapping("/book")
    public ResponseEntity<String> callService(){
        return hystrixService.call();
    }
}
