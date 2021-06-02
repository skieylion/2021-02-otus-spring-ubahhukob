import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.config.EnableWebFlux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import spring.homework.domain.Genre;
import spring.homework.repositories.BookDao;
import spring.homework.repositories.GenreDao;

import static org.junit.jupiter.api.Assertions.assertNotNull;

//перенести в тест
@DataMongoTest
public class BookDaoTest {

    @Autowired
    private BookDao bookDao;

    //private AuthorDao authorDao;
    //private CommentDao commentDao;
    //private BookDao bookDao;
    //,AuthorDao authorDao,CommentDao commentDao,BookDao bookDao GenreDao genreDao

    @Test
    public void test(){
        System.out.println(bookDao);
        /*Mono<Genre> genreMono=genreDao.save(new Genre("6081cc3a3d6d754095f46033","Роман"));
        StepVerifier
                .create(genreMono)
                .assertNext(genre -> assertNotNull(genre.getId()))
                .expectComplete()
                .verify();*/
    }

/*    @ChangeSet(order = "001", id = "initGenre", author = "skieylion", runAlways = true)
    public void initGenre(GenreDao repository){
        genre = repository.save(new Genre("6081cc3a3d6d754095f46033","Роман"));
        repository.save(new Genre("6081cc3a3d6d754095f46031","Фантастика"));
        Mono<Author> author=authorDao.save(new Author("6081cc3a3d6d754095f46027","Алексей Максимович Пешков","М. Горький"));
        Mono<Comment> comment=commentDao.save(new Comment("Отличная книга"));

        genre = repository.save(new Genre("6081cc3a3d6d754095f46033","Роман"));
        repository.save(new Genre("6081cc3a3d6d754095f46031","Фантастика"));
    }

    @ChangeSet(order = "002", id = "initAuthor", author = "skieylion", runAlways = true)
    public void initAuthor(AuthorDao repository){
        author=repository.save(new Author("6081cc3a3d6d754095f46027","Алексей Максимович Пешков","М. Горький"));
    }

    @ChangeSet(order = "003", id = "initComment", author = "skieylion", runAlways = true)
    public void initComment(CommentDao repository){
        comment=repository.save(new Comment("Отличная книга"));

        repository.save(new Comment("1081cc3a3d6d754095f46021","Плохая книга"));
        repository.save(new Comment("2081cc3a3d6d754095f46022","Прекрасная книга"));
        repository.save(new Comment("3081cc3a3d6d754095f46023","Не знаю"));
    }*/
    /*@ChangeSet(order = "004", id = "initBook", author = "skieylion", runAlways = true)
    public void initBook(BookDao bookDao){
        List<Comment> commentList=new ArrayList<>();

        comment.

        bookDao.save(new Book("2222cc3a3d6d754095f46023","Мать",author,genre,List.of(comment1)));

        book=bookDao.save(book);

        Book book2=new Book("3333cc3a3d6d754095f46023","Мать2",author,genre,commentList);
        bookDao.save(book2);

        Book book3=new Book("4444cc3a3d6d754095f46023","Мать3",author,genre,commentList);
        bookDao.save(book3);
    }
    @ChangeSet(order = "005", id = "bookMatchToComment", author = "skieylion", runAlways = true)
    public void bookMatch(CommentDao repository){
        comment.setBook(book);
        comment=repository.save(comment);
    }*/
}
