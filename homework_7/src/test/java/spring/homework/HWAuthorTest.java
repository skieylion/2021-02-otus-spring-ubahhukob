package spring.homework;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import spring.homework.dao.AuthorDaoImpl;
import spring.homework.dao.BookDaoImpl;
import spring.homework.dao.GenreDaoImpl;
import spring.homework.domain.Author;
import spring.homework.domain.Book;
import spring.homework.domain.Genre;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Проверка всех dao")
@JdbcTest
@Import({AuthorDaoImpl.class})
class HWAuthorTest {

    @Autowired
    private AuthorDaoImpl authorDao;

    @DisplayName("read author")
    @Test
    void readAuthor() {
        assertTrue("Александр Сергеевич Пушкин".equals(authorDao.read(2).getFullName()));
    }

    @DisplayName("update author")
    @Test
    void updateAuthor() {
        Author author = new Author(1, "Иванов Иван Иванович", "Иван");

        authorDao.save(author);
        assertSame(authorDao.read(1).getFullName(), "Иванов Иван Иванович");
    }

    @DisplayName("create author")
    @Test
    void createAuthor() {
        Author author = new Author("Шекспир", "?");
        long id = authorDao.save(author);
        assertSame(authorDao.read(id).getFullName(), "Шекспир");
    }

    @DisplayName("delete author")
    @Test
    void deleteAuthor() {
        authorDao.delete(4);
        Exception exception = assertThrows(EmptyResultDataAccessException.class, () -> {
            authorDao.read(4);
        });
        String expectedMessage = "Incorrect result size: expected 1, actual 0";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
