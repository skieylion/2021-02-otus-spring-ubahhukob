package spring.homework.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import spring.homework.domain.Author;
import spring.homework.repositories.AuthorDao;
import spring.homework.repositories.AuthorDaoImpl;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Проверка репозитория автора")
@DataJpaTest
@Import({AuthorDaoImpl.class})
class AuthorDaoImplTest {

    @Autowired
    private AuthorDao authorDao;

    @Autowired
    private TestEntityManager em;

    @DisplayName("read author")
    @Test
    void readAuthor() {
        assertEquals(authorDao.read(2).getFullName(), "Александр Сергеевич Пушкин");
    }

    @DisplayName("update author")
    @Test
    @Transactional
    void updateAuthor() {
        Author author = new Author(1, "Иванов Иван Иванович", "Иван");

        authorDao.save(author);
        assertSame(authorDao.read(1).getFullName(), "Иванов Иван Иванович");
    }

    @DisplayName("create author")
    @Test
    @Transactional
    void createAuthor() {
        Author author = new Author("Шекспир", "?");
        long id = authorDao.save(author);
        assertSame(authorDao.read(id).getFullName(), "Шекспир");
    }

    @DisplayName("delete author")
    @Test
    @Transactional
    void deleteAuthor() {
        authorDao.delete(4);
        assertNull(authorDao.read(4));
    }
}
