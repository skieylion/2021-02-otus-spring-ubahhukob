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
class CommentDaoTest extends AbstractTest {

    @Autowired
    private CommentDao commentDao;
    @Autowired BookDao bookDao;

    @DisplayName("read comment")
    @Test
    void readComment() {
        assertEquals("Прекрасная книга",commentDao.findById("2081cc3a3d6d754095f46022").get().getDescription());
    }

    @DisplayName("update comment")
    @Test
    void updateComment() {
        Comment comment = commentDao.findById("1081cc3a3d6d754095f46021").get();
        comment.setDescription("интересная книга");
        commentDao.save(comment);
        assertEquals(commentDao.findById(comment.getId()).get().getDescription(),"интересная книга");
    }

    @DisplayName("create comment")
    @Test
    void createComment() {
        Book book=bookDao.findById("2222cc3a3d6d754095f46023").get();
        Comment comment = new Comment("Так так");
        comment.setBook(book);
        comment=commentDao.save(comment);
        assertEquals(commentDao.findById(comment.getId()).get().getDescription(),"Так так");
        Comment comment2=commentDao.findById(comment.getId()).get();
        assertEquals(comment2.getBook().getId(),"2222cc3a3d6d754095f46023");
    }

    @DisplayName("delete comment")
    @Test
    void deleteComment() {
        commentDao.deleteById("3081cc3a3d6d754095f46023");
        assertTrue(commentDao.findById("3081cc3a3d6d754095f46023").isEmpty());
    }

}
