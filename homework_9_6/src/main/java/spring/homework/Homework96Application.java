package spring.homework;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class Homework96Application {

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(Homework96Application.class, args);
        //Console.main(args);
    }

}
