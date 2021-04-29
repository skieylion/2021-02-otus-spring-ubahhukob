package spring.homework;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import spring.homework.domain.Author;
import spring.homework.domain.Book;
import spring.homework.domain.Comment;
import spring.homework.domain.Genre;
import spring.homework.repositories.AuthorDao;
import spring.homework.repositories.BookDao;
import spring.homework.repositories.CommentDao;
import spring.homework.repositories.GenreDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Homework96Application {

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(Homework96Application.class, args);
    }

}
