package spring.homework;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import spring.homework.domain.Author;
import spring.homework.domain.Book;
import spring.homework.domain.Comment;
import spring.homework.domain.Genre;
import spring.homework.repositories.AuthorDao;
import spring.homework.repositories.BookDao;
import spring.homework.repositories.CommentDao;
import spring.homework.repositories.GenreDao;

import java.sql.SQLException;

@SpringBootApplication
public class Homework96Application {

	public static void main(String[] args) throws SQLException, InterruptedException {
		var context=SpringApplication.run(Homework96Application.class, args);
		GenreDao repo=context.getBean(GenreDao.class);
		AuthorDao repo2=context.getBean(AuthorDao.class);
		BookDao repo3=context.getBean(BookDao.class);
		CommentDao repo4=context.getBean(CommentDao.class);

		System.out.println("----------------");

		//System.out.println(repo3.read(1));
		//repo3.delete(1);

		Author author=new Author(1,"KEKS","asd");
		Genre genre=new Genre("HT");
		Comment comment=new Comment("Новый");

		//repo4.save(comment);
		//System.out.println(repo4.read(2));
		//repo4.delete(1);
		Book book=new Book("KIK",author,genre,comment);
		repo3.save(book);
		System.out.println(repo3.readAll());

		//long id=repo2.save(new Author("asd","HJJ"));
		//System.out.println(repo2.read(1).toString());
		//repo2.delete(1);
		//System.out.println(repo.read(1).toString());
		//repo.delete(1);
		//long id=repo.save(new Genre("HJJ"));
		//long id2=repo.save(new Genre("H222JJ"));
		//System.out.println(id2);
		//repo.read(1);
		System.out.println("----------------");
		//repository.deleteAll();
		Console.main(args);
	}

}
