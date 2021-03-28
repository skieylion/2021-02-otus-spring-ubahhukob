package spring.homework;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import spring.homework.domain.Book;
import spring.homework.repositories.CourseRepository;

import java.sql.SQLException;
import java.util.Iterator;

@SpringBootApplication
public class Homework96Application {

	public static void main(String[] args) throws SQLException, InterruptedException {
		var context=SpringApplication.run(Homework96Application.class, args);
		CourseRepository repository=context.getBean(CourseRepository.class);

		System.out.println("----------------");
		repository.test();
		System.out.println("----------------");
		//repository.deleteAll();
		Console.main(args);
	}

}
