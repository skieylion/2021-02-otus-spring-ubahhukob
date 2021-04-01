package spring.homework;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import spring.homework.domain.Author;
import spring.homework.domain.Book;
import spring.homework.domain.Comment;
import spring.homework.domain.Genre;
import spring.homework.repositories.AuthorDaoImpl;
import spring.homework.repositories.BookDaoImpl;
import spring.homework.repositories.CommentDaoImpl;
import spring.homework.repositories.GenreDaoImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Проверка репозитория книг")
@DataJpaTest
@Import({BookDaoImpl.class, GenreDaoImpl.class, AuthorDaoImpl.class, CommentDaoImpl.class})
class BookDaoImplTest {

    @Autowired
    private BookDaoImpl bookDao;

    @Autowired
    private TestEntityManager em;


    @DisplayName("read book")
    @Test
    void readBook() {
        Book book=bookDao.read(2);
        assertTrue("Ruslan and Ludmila".equals(book.getName()));
    }

    @DisplayName("read book all")
    @Test
    void readBookAll() {
        assertTrue(bookDao.readAll().size() > 0);
    }

    @DisplayName("update book")
    @Test
    void updateBook() {
        Author author = new Author(1, "Иванов Иван Иванович", "Иван");
        Genre genre = new Genre(1, "Любой жанр");
        List<Comment> comment = new ArrayList<>();
        comment.add(new Comment("Какой-то коммент"));

        Book book = new Book(1, "Детство2", author, genre, comment);
        bookDao.save(book);
        assertSame(bookDao.read(1).getName(), "Детство2");
    }

    @DisplayName("delete book")
    @Test
    void deleteBook() {
        bookDao.delete(3);
        assertNull(bookDao.read(3));
    }

    @DisplayName("create book")
    @Test
    void createBook() {
        Author author = new Author("Иванов Иван Иванович", "Иван2");
        Genre genre = new Genre("Любой жанр2");
        Comment comment = new Comment("Комментарий ...");
        List<Comment> comments=new ArrayList<>();
        comments.add(comment);

        Book book = new Book("Новая книга2", author, genre, comments);
        long id = bookDao.save(book);
        assertSame(bookDao.read(id).getName(), "Новая книга2");
    }
}
