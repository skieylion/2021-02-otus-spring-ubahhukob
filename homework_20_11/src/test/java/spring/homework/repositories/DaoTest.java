package spring.homework.repositories;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import spring.homework.domain.Author;
import spring.homework.domain.Book;
import spring.homework.domain.Comment;
import spring.homework.domain.Genre;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

//перенести в тест
@DataMongoTest
public class DaoTest {

    @Autowired
    private GenreDao genreDao;
    @Autowired
    private AuthorDao authorDao;
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private BookDao bookDao;

    @Test
    public void test(){
        Author author=new Author("6081cc3a3d6d754095f46031","Пушкин","Роман");
        Genre genre=new Genre("6081cc3a3d6d754095f46033","Роман");
        Comment comment=new Comment("1081cc3a3d6d754095f46031","Коммент");
        Book book=new Book("Книга",author,genre, Arrays.asList(comment));

        Mono<Genre> genreMono=genreDao.save(genre);
        Mono<Author> authorMono=authorDao.save(author);
        Mono<Comment> commentMono=commentDao.save(comment);
        Mono<Book> bookMono=bookDao.save(book);

        StepVerifier
        .create(genreMono)
        .assertNext(_genre -> assertNotNull(_genre.getId()))
        .expectComplete()
        .verify();

        StepVerifier
        .create(authorMono)
        .assertNext(_author -> assertNotNull(_author.getId()))
        .expectComplete()
        .verify();

        StepVerifier
        .create(commentMono)
        .assertNext(_comment -> assertNotNull(_comment.getId()))
        .expectComplete()
        .verify();

        StepVerifier
        .create(bookMono)
        .assertNext(_book -> assertNotNull(_book.getId()))
        .expectComplete()
        .verify();

    }
}
