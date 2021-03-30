package spring.homework;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import spring.homework.domain.Author;
import spring.homework.domain.Book;
import spring.homework.domain.Comment;
import spring.homework.domain.Genre;
import spring.homework.repositories.AuthorDaoImpl;
import spring.homework.repositories.BookDaoImpl;
import spring.homework.repositories.GenreDaoImpl;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Проверка репозитория книг")
@SpringBootTest
@Import({BookDaoImpl.class, GenreDaoImpl.class, AuthorDaoImpl.class})
class BookDaoImplTest {

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
        Comment comment = new Comment("Какой-то коммент");

        Book book = new Book(1, "Детство2", author, genre, comment);
        bookDao.save(book);
        assertSame(bookDao.read(1).getName(), "Детство2");
    }

    @DisplayName("delete book")
    @Test
    void deleteBook() {
        bookDao.delete(3);
        assertNull(bookDao.read(3));
    }

    @DisplayName("create book")
    @Test
    void createBook() {
        Author author = new Author("Иванов Иван Иванович", "Иван2");
        Genre genre = new Genre("Любой жанр2");
        Comment comment = new Comment("Комментарий ...");
        Book book = new Book("Новая книга2", author, genre, comment);
        long id = bookDao.save(book);
        assertSame(bookDao.read(id).getName(), "Новая книга2");
    }
}
