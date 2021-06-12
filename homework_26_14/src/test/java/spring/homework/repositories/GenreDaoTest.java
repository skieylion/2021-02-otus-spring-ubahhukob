package spring.homework.repositories;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import spring.homework.domain.Genre;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Проверка репозитория жанров")
class GenreDaoTest extends AbstractTest {

    @Autowired
    private GenreDao genreDao;

    @DisplayName("read genre")
    @Test
    void readGenre() {
        assertEquals("Роман",genreDao.findById("6081cc3a3d6d754095f46033").get().getName());
    }

    @DisplayName("update genre")
    @Test
    void updateGenre() {
        Genre genre = genreDao.findById("6081cc3a3d6d754095f46031").get();
        genre.setName("Классика");
        genreDao.save(genre);
        assertEquals("Классика",genreDao.findById("6081cc3a3d6d754095f46031").get().getName());
    }

    @DisplayName("create genre")
    @Test
    void createGenre() {
        Genre genre = new Genre("Новый жанр");
        genre=genreDao.save(genre);
        assertEquals(genreDao.findById(genre.getId()).get().getName(),"Новый жанр");
    }

    @DisplayName("delete genre")
    @Test
    void deleteGenre() {
        Genre genre = new Genre("Новый жанр2");
        genre=genreDao.save(genre);
        genreDao.deleteById(genre.getId());
        assertTrue(genreDao.findById(genre.getId()).isEmpty());
    }

}
