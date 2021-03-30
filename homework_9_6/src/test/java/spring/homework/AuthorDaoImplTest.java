package spring.homework;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import spring.homework.domain.Author;
import spring.homework.repositories.AuthorDao;
import spring.homework.repositories.AuthorDaoImpl;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Проверка репозитория автора")
@SpringBootTest
@Import({AuthorDaoImpl.class})
class AuthorDaoImplTest {

    @Autowired
    private AuthorDao authorDao;

    @DisplayName("read author")
    @Test
    void readAuthor() {
        assertEquals(authorDao.read(2).getFullName(), "Александр Сергеевич Пушкин");
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
        authorDao.delete(3);
        assertNull(authorDao.read(3));
    }
}
