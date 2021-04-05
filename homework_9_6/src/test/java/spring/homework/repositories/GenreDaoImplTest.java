package spring.homework.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;
import spring.homework.domain.Genre;
import spring.homework.repositories.GenreDaoImpl;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Проверка репозитория жанров")
@DataJpaTest
@Import({GenreDaoImpl.class})
class GenreDaoImplTest {

    @Autowired
    private GenreDaoImpl genreDao;

    @Autowired
    private TestEntityManager em;

    @DisplayName("read genre")
    @Test
    void readGenre() {
        assertTrue("Классика".equals(genreDao.read(4).getName()));
    }

    @DisplayName("update genre")
    @Test
    @Transactional
    void updateGenre() {
        Genre genre = new Genre(4, "Проза");

        genreDao.save(genre);
        assertSame(genreDao.read(4).getName(), "Проза");
    }

    @DisplayName("create genre")
    @Test
    @Transactional
    void createGenre() {
        Genre genre = new Genre("Новый жанр");
        long id = genreDao.save(genre);
        assertSame(genreDao.read(id).getName(), "Новый жанр");
    }

    @DisplayName("delete genre")
    @Test
    @Transactional
    void deleteGenre() {
        genreDao.delete(5);
        assertNull(genreDao.read(5));
    }
}
