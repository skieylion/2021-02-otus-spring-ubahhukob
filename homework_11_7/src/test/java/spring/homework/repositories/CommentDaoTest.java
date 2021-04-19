package spring.homework.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import spring.homework.domain.Book;
import spring.homework.domain.Comment;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Проверка репозитория комментария")
@DataJpaTest
class CommentDaoTest {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private TestEntityManager em;

    @DisplayName("read comment")
    @Test
    void readComment() {
        assertTrue("Какая прекрасная книга".equals(commentDao.findById(1L).get().getDescription()));
    }

    @DisplayName("update comment")
    @Test
    void updateComment() {
        Comment comment = new Comment(2, "Новая книга");
        commentDao.save(comment);
        assertSame(commentDao.findById(2L).get().getDescription(), "Новая книга");
    }

    @DisplayName("create comment")
    @Test
    void createComment() {
        Comment comment = new Comment("Так так");
        Book book=new Book(1,"Мать");
        comment.setBook(book);
        long id = commentDao.save(comment).getId();
        assertSame(commentDao.findById(id).get().getDescription(), "Так так");
    }

    @DisplayName("delete comment")
    @Test
    void deleteComment() {
        commentDao.deleteById(4L);
        assertTrue(commentDao.findById(4L).isEmpty());
    }
}
