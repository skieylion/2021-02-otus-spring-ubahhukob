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
@Import({GenreDaoImpl.class})
class HWGenreTest {

    @Autowired
    private GenreDaoImpl genreDao;

    @DisplayName("read genre")
    @Test
    void readGenre() {
        assertTrue("Фантастика".equals(genreDao.read(3).getName()));
    }

    @DisplayName("update genre")
    @Test
    void updateGenre() {
        Genre genre = new Genre(4, "Проза");

        genreDao.save(genre);
        assertSame(genreDao.read(4).getName(), "Проза");
    }

    @DisplayName("create genre")
    @Test
    void createGenre() {
        Genre genre = new Genre("Новый жанр");
        long id = genreDao.save(genre);
        assertSame(genreDao.read(id).getName(), "Новый жанр");
    }

    @DisplayName("delete genre")
    @Test
    void deleteGenre() {
        genreDao.delete(5);
        Exception exception = assertThrows(EmptyResultDataAccessException.class, () -> {
            genreDao.read(5);
        });
        String expectedMessage = "Incorrect result size: expected 1, actual 0";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
