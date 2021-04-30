package spring.homework.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import spring.homework.controllers.BookController;
import spring.homework.controllers.MainController;
import spring.homework.domain.Author;
import spring.homework.domain.Book;
import spring.homework.domain.Genre;
import spring.homework.repositories.BookDao;
import spring.homework.services.ServiceBook;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
@DisplayName("web crud")
class BookWebTest {

    //https://spring.io/guides/gs/testing-web/

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ServiceBook serviceBook;

    @DisplayName("read book")
    @Test
    void readBook() throws Exception {
        Book book=new Book("Книга",new Author("Автор",""),new Genre("Жанр"),new ArrayList<>());

        when(serviceBook.read("3333cc3a3d6d754095f46023"))
        .thenReturn(book);

        mvc
        .perform(get("/api/find/3333cc3a3d6d754095f46023"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Книга")));
    }

    @DisplayName("read book all")
    @Test
    void readBookAll() throws Exception {
        List<Book> books=new ArrayList<>();
        books.add(new Book("Книга1",new Author("Автор",""),new Genre("Жанр"),new ArrayList<>()));
        books.add(new Book("Книга2",new Author("Автор",""),new Genre("Жанр"),new ArrayList<>()));

        when(serviceBook.readAll())
        .thenReturn(books);

        mvc
        .perform(get("/api/find"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Книга1")))
        .andExpect(content().string(containsString("Книга2")));
    }

    @DisplayName("update book")
    @Test
    void updateBook() throws Exception {

        Book book=new Book("3333cc3a3d6d754095f46023","Название1");

        Mockito
        .doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                String id=invocation.getArgument(0);
                String name=invocation.getArgument(1);
                book.setId(id);
                book.setName(name);
                return null;
            }
        })
        .when(serviceBook)
        .update("3553cc3a3d6d754095f46023","Название2");

        mvc
        .perform(get("/api/update/3553cc3a3d6d754095f46023?name=Название2"))
        .andDo(print())
        .andExpect(status().is3xxRedirection());
    }

    @DisplayName("delete book")
    @Test
    void deleteBook() throws Exception {
        when(serviceBook.delete("4551cc3a3d6d754095f46023"))
        .thenReturn(true);

        mvc
        .perform(get("/api/delete/4551cc3a3d6d754095f46023"))
        .andDo(print())
        .andExpect(status().isOk());
    }

    @DisplayName("create book")
    @Test
    void createBook() throws Exception {
        Book book=new Book("Книга",new Author("Автор",""),new Genre("Жанр"),new ArrayList<>());

        when(serviceBook.create("Книга","Автор","Жанр","Коммент"))
        .thenReturn("2233cc3a3d6d754095f46023");

        mvc
        .perform(post("/api/create?name=Книга&author=Автор&genre=Жанр&comment=Коммент"))
        .andDo(print())
        .andExpect(status().is3xxRedirection());
    }
}