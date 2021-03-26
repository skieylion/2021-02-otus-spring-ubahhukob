package spring.homework;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import spring.homework.dao.*;
import spring.homework.domain.Author;
import spring.homework.domain.Book;
import spring.homework.domain.Genre;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Проверка всех dao")
@JdbcTest
@Import({BookDaoImpl.class, AuthorDaoImpl.class,GenreDaoImpl.class})
class Homework7ApplicationTests {

	@Autowired
	private BookDaoImpl bookDao;

	@Autowired
	private AuthorDaoImpl authorDao;

	@Autowired
	private GenreDaoImpl genreDao;

	@DisplayName("read book")
	@Test
	void readBook(){
		assertTrue("Ruslan and Ludmila".equals(bookDao.read(2).getName()));
	}

	@DisplayName("read book all")
	@Test
	void readBookAll(){
		assertTrue(bookDao.readAll().size()>0);
	}

	@DisplayName("update book")
	@Test
	void updateBook(){
		Author author=new Author(1,"Иванов Иван Иванович","Иван");
		Genre genre=new Genre(1,"Любой жанр");

		Book book = new Book(1,"Детство2",author,genre);
		bookDao.update(book);
		assertSame(bookDao.read(1).getName(),"Детство2");
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
		Author author=new Author("Иванов Иван Иванович","Иван2");
		Genre genre=new Genre("Любой жанр2");
		Book book=new Book("Новая книга2",author,genre);
		long id=bookDao.create(book);
		assertSame(bookDao.read(id).getName(),"Новая книга2");
	}

	@DisplayName("read genre")
	@Test
	void readGenre(){
		assertTrue("Фантастика".equals(genreDao.read(3).getName()));
	}

	@DisplayName("update genre")
	@Test
	void updateGenre(){
		Genre genre=new Genre(4,"Проза");

		genreDao.update(genre);
		assertSame(genreDao.read(4).getName(),"Проза");
	}
	@DisplayName("create genre")
	@Test
	void createGenre(){
		Genre genre=new Genre("Новый жанр");
		long id=genreDao.create(genre);
		assertSame(genreDao.read(id).getName(),"Новый жанр");
	}
	@DisplayName("delete genre")
	@Test
	void deleteGenre(){
		genreDao.delete(5);
		Exception exception = assertThrows(EmptyResultDataAccessException.class, () -> {
			genreDao.read(5);
		});
		String expectedMessage = "Incorrect result size: expected 1, actual 0";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@DisplayName("read author")
	@Test
	void readAuthor(){
		assertTrue("Александр Сергеевич Пушкин".equals(authorDao.read(2).getFullName()));
	}

	@DisplayName("update author")
	@Test
	void updateAuthor(){
		Author author=new Author(1,"Иванов Иван Иванович","Иван");

		authorDao.update(author);
		assertSame(authorDao.read(1).getFullName(),"Иванов Иван Иванович");
	}
	@DisplayName("create author")
	@Test
	void createAuthor(){
		Author author=new Author("Шекспир","?");
		long id=authorDao.create(author);
		assertSame(authorDao.read(id).getFullName(),"Шекспир");
	}
	@DisplayName("delete author")
	@Test
	void deleteAuthor(){
		authorDao.delete(4);
		Exception exception = assertThrows(EmptyResultDataAccessException.class, () -> {
			authorDao.read(4);
		});
		String expectedMessage = "Incorrect result size: expected 1, actual 0";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}
}
