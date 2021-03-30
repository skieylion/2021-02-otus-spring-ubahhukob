package spring.homework;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import spring.homework.domain.Comment;
import spring.homework.repositories.CommentDaoImpl;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Проверка репозитория комментария")
@SpringBootTest
@Import({CommentDaoImpl.class})
class CommentDaoImplTest {

    @Autowired
    private CommentDaoImpl commentDao;

    @DisplayName("read comment")
    @Test
    void readComment() {
        assertTrue("Какая прекрасная книга".equals(commentDao.read(1).getDescription()));
    }

    @DisplayName("update comment")
    @Test
    void updateComment() {
        Comment comment = new Comment(2, "Новая книга");

        commentDao.save(comment);
        assertSame(commentDao.read(2).getDescription(), "Новая книга");
    }

    @DisplayName("create comment")
    @Test
    void createComment() {
        Comment comment = new Comment("Так так");
        long id = commentDao.save(comment);
        assertSame(commentDao.read(id).getDescription(), "Так так");
    }

    @DisplayName("delete comment")
    @Test
    void deleteComment() {
        commentDao.delete(4);
        assertNull(commentDao.read(4));
    }
}
