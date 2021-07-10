package spring.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.sql.SQLException;


@SpringBootApplication
@EnableWebMvc
public class App {

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(App.class, args);
    }

}
