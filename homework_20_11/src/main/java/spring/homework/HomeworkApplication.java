package spring.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

import java.sql.SQLException;

@SpringBootApplication
public class HomeworkApplication {

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(HomeworkApplication.class, args);
    }

}
