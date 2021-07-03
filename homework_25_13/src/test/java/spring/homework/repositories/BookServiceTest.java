package spring.homework.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.annotation.SecurityTestExecutionListeners;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import spring.homework.controllers.BookController;
import spring.homework.domain.Author;
import spring.homework.domain.Book;
import spring.homework.domain.Genre;
import spring.homework.services.ServiceBook;
import spring.homework.services.ServiceBookImpl;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;
import java.util.ArrayList;

import static org.mockito.Mockito.when;

@DisplayName("book service test")
@SpringBootTest
@AutoConfigureMockMvc
class BookServiceTest {

    @Autowired
    private MockMvc mvc;

    @DisplayName("reading all book")
    @Test
    @WithMockUser(roles = {"USER"})
    void readBookAll() throws Exception {
        mvc.perform(get("/book"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Mother")))
            .andExpect(content().string(containsString("Ruslan and Ludmila")));
    }
    @DisplayName("reading a book")
    @Test
    @WithMockUser(roles = {"USER"})
    void readBook() throws Exception {
        mvc.perform(get("/book/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Ruslan and Ludmila")));
    }
    @DisplayName("update a book")
    @Test
    @WithMockUser(roles = {"EDITOR"})
    void updateBook() throws Exception {
        mvc.perform(put("/book").contentType(MediaType.APPLICATION_JSON).content("{\"id\":1,\"name\":\"testName\"}")).andExpect(status().isOk());
        mvc.perform(get("/book/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("testName")));
    }
    @DisplayName("delete a book")
    @Test
    @WithMockUser(roles = {"EDITOR"})
    void deleteBook() throws Exception {
        mvc.perform(delete("/book/3"))
                .andExpect(status().isOk());
        mvc.perform(get("/book/3"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
}