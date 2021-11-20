package spring.project.engine;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableRabbit
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }
}
