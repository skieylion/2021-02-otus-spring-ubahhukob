package spring.homework.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import spring.homework.domain.Genre;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Проверка репозитория жанров")
@DataJpaTest
class GenreDaoTest {

    @Autowired
    private GenreDao genreDao;

    @Autowired
    private TestEntityManager em;

    @DisplayName("read genre")
    @Test
    void readGenre() {
        assertTrue("Классика".equals(genreDao.findById(4L).get().getName()));
    }

    @DisplayName("update genre")
    @Test
    void updateGenre() {
        Genre genre = new Genre(4, "Проза");

        genreDao.save(genre);
        assertSame(genreDao.findById(4L).get().getName(), "Проза");
    }

    @DisplayName("create genre")
    @Test
    void createGenre() {
        Genre genre = new Genre("Новый жанр");
        long id = genreDao.save(genre).getId();
        assertSame(genreDao.findById(id).get().getName(), "Новый жанр");
    }

    @DisplayName("delete genre")
    @Test
    void deleteGenre() {
        genreDao.deleteById(5L);
        assertTrue(genreDao.findById(5L).isEmpty());
    }
}
