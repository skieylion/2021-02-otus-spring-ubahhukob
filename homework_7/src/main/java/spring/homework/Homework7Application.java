package spring.homework;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.shell.standard.ShellComponent;
import spring.homework.dao.AuthorDao;
import spring.homework.dao.BookDao;
import spring.homework.dao.GenreDao;
import spring.homework.domain.Book;

import java.sql.SQLException;

@SpringBootApplication
public class Homework7Application {

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(Homework7Application.class, args);
        //Console.main(args);
    }

}