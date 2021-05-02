package spring.homework.repositories;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import spring.homework.domain.Author;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Проверка репозитория автора")
class AuthorDaoTest extends AbstractTest {

    @Autowired
    private AuthorDao authorDao;

    @DisplayName("read author")
    @Test
    void readAuthor() {
        assertEquals(authorDao.findById("6081cc3a3d6d754095f46027").get().getFullName(),"Алексей Максимович Пешков");
    }

    @DisplayName("update author")
    @Test
    void updateAuthor() {
        Author author = authorDao.findById("6081cc3a3d6d754095f46027").get();
        author.setAlias("Максим Горький");
        authorDao.save(author);
        assertEquals(authorDao.findById("6081cc3a3d6d754095f46027").get().getAlias(),"Максим Горький");
    }

    @DisplayName("create author")
    @Test
    void createAuthor() {
        Author author = new Author("Шекспир", "?");
        author=authorDao.save(author);
        assertEquals(authorDao.findById(author.getId()).get().getFullName(),"Шекспир");
    }

    @DisplayName("delete author")
    @Test
    void deleteAuthor() {
        Author author = new Author("Какой-то автор", "?");
        author=authorDao.save(author);
        String id=author.getId();
        authorDao.deleteById(id);
        assertTrue(authorDao.findById(id).isEmpty());
    }

}
