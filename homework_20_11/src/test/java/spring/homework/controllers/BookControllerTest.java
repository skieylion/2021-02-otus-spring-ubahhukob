package spring.homework.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import spring.homework.controllers.BookController;
import spring.homework.domain.Author;
import spring.homework.domain.Book;
import spring.homework.domain.Comment;
import spring.homework.domain.Genre;
import spring.homework.repositories.BookDao;

import java.util.ArrayList;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@WebFluxTest(controllers = BookController.class)
public class BookControllerTest {

    @MockBean
    BookDao bookDao;

    @Autowired
    private BookController bookController;

    @BeforeEach
    public void setUp(){
        Author author=new Author("6081cc3a3d6d754095f46031","Пушкин","Роман");
        Genre genre=new Genre("6081cc3a3d6d754095f46033","Роман");
        Comment comment=new Comment("1081cc3a3d6d754095f46031","Коммент");
        Book book=new Book("Книга",author,genre, Arrays.asList(comment));
        when(bookDao.findById("6096f6c277bea85fe39529f3")).thenReturn(Mono.just(book));
    }

    @Test
    public void test() {
        Book book=bookController.find("6096f6c277bea85fe39529f3").block();
        assertEquals(book.getName(),"Книга");
        assertEquals(book.getGenre().getName(),"Роман");
        assertEquals(book.getAuthor().getFullName(),"Пушкин");
        assertEquals(book.getComments().get(0).getDescription(),"Коммент");
    }
}
