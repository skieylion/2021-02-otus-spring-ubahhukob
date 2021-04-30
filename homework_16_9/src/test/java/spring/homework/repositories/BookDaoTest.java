package spring.homework.repositories;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import spring.homework.domain.Author;
import spring.homework.domain.Book;
import spring.homework.domain.Comment;
import spring.homework.domain.Genre;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Проверка репозитория книг")
class BookDaoTest extends AbstractTest {

    @Autowired
    private BookDao bookDao;
    @Autowired
    private CommentDao commentDao;

    @DisplayName("read book")
    @Test
    void readBook() {
        Book book=bookDao.findById("2222cc3a3d6d754095f46023").get();
        assertEquals("Мать",book.getName());
        assertEquals(book.getAuthor().getFullName(),"Алексей Максимович Пешков");
        assertEquals(book.getGenre().getName(),"Роман");
        assertEquals(book.getComments().get(0).getDescription(),"Отличная книга");
    }

    @DisplayName("read book all")
    @Test
    void readBookAll() {
        List<Book> books= bookDao.findAll();
        assertTrue(books.size() > 0);
    }

    @DisplayName("update book")
    @Test
    void updateBook() {
        Book book=bookDao.findById("3333cc3a3d6d754095f46023").get();
        book.setName("Мать3");
        book=bookDao.save(book);
        assertEquals(bookDao.findById("3333cc3a3d6d754095f46023").get().getName(), "Мать3");
    }

    @DisplayName("delete book")
    @Test
    void deleteBook() {
        bookDao.deleteById("4444cc3a3d6d754095f46023");
        assertTrue(bookDao.findById("4444cc3a3d6d754095f46023").isEmpty());
    }

    @DisplayName("create book")
    @Test
    void createBook() {
        Author author = new Author("Иванов Иван Иванович", "Иван2");
        Genre genre = new Genre("Любой жанр2");
        Comment comment = new Comment(new ObjectId().toHexString(),"Комментарий ...");
        List<Comment> comments=new ArrayList<>();
        comments.add(comment);
        Book book = new Book("Новая книга2", author, genre, comments);

        book=bookDao.save(book);
        comment.setBook(book);
        commentDao.save(comment);

        assertEquals(bookDao.findById(book.getId()).get().getName(),"Новая книга2");
    }
}