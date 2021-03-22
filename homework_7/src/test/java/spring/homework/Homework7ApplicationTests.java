package spring.homework;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.TestPropertySource;
import spring.homework.dao.AuthorDao;
import spring.homework.dao.BookDao;
import spring.homework.dao.GenreDao;
import spring.homework.domain.Book;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("classpath:application.properties")
class Homework7ApplicationTests {

	@Autowired
	BookDao bookDao;


	@DisplayName("read book")
	@Test
	void readBook(){
		assertTrue("Ruslan and Ludmila".equals(bookDao.read(2).getName()));
	}

	@DisplayName("update book")
	@Test
	void updateBook(){
		Book book = new Book(1,"Детство",1,1);
		bookDao.update(book);
		assertSame(bookDao.read(1).getName(),"Детство");
	}

	@DisplayName("delete book")
	@Test
	void deleteBook(){
		bookDao.delete(3);
		Exception exception = assertThrows(EmptyResultDataAccessException.class, () -> {
			bookDao.read(3);
		});
		String expectedMessage = "Incorrect result size: expected 1, actual 0";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@DisplayName("create book")
	@Test
	void createBook(){
		Book book=new Book("Новая книга",1,1);
		long id=bookDao.create(book);
		assertSame(bookDao.read(id).getName(),"Новая книга");
	}



}
