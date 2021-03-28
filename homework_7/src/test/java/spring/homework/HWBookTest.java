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
@Import({BookDaoImpl.class, GenreDaoImpl.class, AuthorDaoImpl.class})
class HWBookTest {

    @Autowired
    private BookDaoImpl bookDao;

    @DisplayName("read book")
    @Test
    void readBook() {
        assertTrue("Ruslan and Ludmila".equals(bookDao.read(2).getName()));
    }

    @DisplayName("read book all")
    @Test
    void readBookAll() {
        assertTrue(bookDao.readAll().size() > 0);
    }

    @DisplayName("update book")
    @Test
    void updateBook() {
        Author author = new Author(1, "Иванов Иван Иванович", "Иван");
        Genre genre = new Genre(1, "Любой жанр");

        Book book = new Book(1, "Детство2", author, genre);
        bookDao.save(book);
        assertSame(bookDao.read(1).getName(), "Детство2");
    }

    @DisplayName("delete book")
    @Test
    void deleteBook() {
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
    void createBook() {
        Author author = new Author("Иванов Иван Иванович", "Иван2");
        Genre genre = new Genre("Любой жанр2");
        Book book = new Book("Новая книга2", author, genre);
        long id = bookDao.save(book);
        assertSame(bookDao.read(id).getName(), "Новая книга2");
    }
}
