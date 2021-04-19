package spring.homework.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import spring.homework.domain.Author;
import spring.homework.domain.Book;
import spring.homework.domain.Comment;
import spring.homework.domain.Genre;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Проверка репозитория книг")
@DataJpaTest
class BookDaoTest {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private TestEntityManager em;


    @DisplayName("read book")
    @Test
    void readBook() {
        Book book=bookDao.findById(2L).get();
        assertEquals("Ruslan and Ludmila",book.getName());
    }

    @DisplayName("read book all")
    @Test
    void readBookAll() {
        List<Book> books= bookDao.findAll();
        //System.out.println(books);
        assertTrue(books.size() > 0);
    }

    @DisplayName("update book")
    @Test
    void updateBook() {
        Author author = new Author(1, "Иванов Иван Иванович", "Иван");
        Genre genre = new Genre(1, "Любой жанр");
        List<Comment> comment = new ArrayList<>();
        comment.add(new Comment("Какой-то коммент"));

        Book book = new Book(1, "Детство2", author, genre, comment);
        comment.get(0).setBook(book);
        bookDao.save(book);
        assertSame(bookDao.findById(1L).get().getName(), "Детство2");
    }

    @DisplayName("delete book")
    @Test
    void deleteBook() {
        bookDao.deleteById(3L);
        assertTrue(bookDao.findById(3L).isEmpty());
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
        comment.setBook(book);
        long id = bookDao.save(book).getId();
        em.detach(book);
        Book book2=bookDao.findById(id).get();
        assertEquals(1,book2.getComments().size());
        assertSame(book2.getName(), "Новая книга2");
    }
}
