package spring.homework.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import spring.homework.domain.Author;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Проверка репозитория автора")
@DataJpaTest
class AuthorDaoTest {

    @Autowired
    private AuthorDao authorDao;

    @Autowired
    private TestEntityManager em;

    @DisplayName("read author")
    @Test
    void readAuthor() {
        assertEquals(authorDao.findById(2L).get().getFullName(), "Александр Сергеевич Пушкин");
    }

    @DisplayName("update author")
    @Test
    void updateAuthor() {
        Author author = new Author(1, "Иванов Иван Иванович", "Иван");

        authorDao.save(author);
        assertSame(authorDao.findById(1L).get().getFullName(), "Иванов Иван Иванович");
    }

    @DisplayName("create author")
    @Test
    void createAuthor() {
        Author author = new Author("Шекспир", "?");
        long id = (authorDao.<Author>save(author)).getId();
        assertSame(authorDao.findById(id).get().getFullName(), "Шекспир");
    }

    @DisplayName("delete author")
    @Test
    void deleteAuthor() {
        authorDao.deleteById(4L);
        assertTrue(authorDao.findById(4L).isEmpty());
    }
}
