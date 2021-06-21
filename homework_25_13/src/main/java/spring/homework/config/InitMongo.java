package spring.homework.config;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.spring.v5.EnableMongock;
import com.mongodb.client.MongoDatabase;
import org.springframework.context.annotation.Configuration;
import spring.homework.domain.*;
import spring.homework.repositories.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableMongock
@ChangeLog(order = "001")
public class InitMongo {

    private Genre genre;
    private Author author;
    private Comment comment;
    private Book book;

    @ChangeSet(order = "000", id = "dropDB", author = "skieylion", runAlways = true)
    public void dropDB(MongoDatabase database){
        database.drop();
    }

    @ChangeSet(order = "001", id = "initGenre", author = "skieylion", runAlways = true)
    public void initGenre(GenreDao repository){
        genre = repository.save(new Genre("6081cc3a3d6d754095f46033","Роман"));
        repository.save(new Genre("6081cc3a3d6d754095f46031","Фантастика"));
    }

    @ChangeSet(order = "002", id = "initAuthor", author = "skieylion", runAlways = true)
    public void initAuthor(AuthorDao repository){
        author=repository.save(new Author("6081cc3a3d6d754095f46027","Алексей Максимович Пешков","М. Горький"));
    }

    @ChangeSet(order = "003", id = "initComment", author = "skieylion", runAlways = true)
    public void initComment(CommentDao repository){
        comment = new Comment("Отличная книга");
        comment=repository.save(comment);

        repository.save(new Comment("1081cc3a3d6d754095f46021","Плохая книга"));
        repository.save(new Comment("2081cc3a3d6d754095f46022","Прекрасная книга"));
        repository.save(new Comment("3081cc3a3d6d754095f46023","Не знаю"));
    }
    @ChangeSet(order = "004", id = "initBook", author = "skieylion", runAlways = true)
    public void initBook(BookDao bookDao){
        List<Comment> commentList=new ArrayList<>();
        commentList.add(comment);
        book=new Book("2222cc3a3d6d754095f46023","Мать",author,genre,commentList);
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
    }
    @ChangeSet(order = "006", id = "initUser", author = "skieylion", runAlways = true)
    public void initUser(UserDao userDao){
        User user=new User();
        user.setLogin("user");
        user.setName("Юзеров Юзер Юзерович");
        user.setPassword("$2y$04$SpZYHxUB6DnOpBsIRVglLuXgB.bbnH5uwbyyY0l9hF5Ghc3y2b.2a");
        Role role=new Role();
        role.setName("USER");
        role.setPermissions(new ArrayList<String>(Arrays.asList(new String[]{Permission.READ.get()})));
        user.setRole(role);
        userDao.save(user);
    }
    @ChangeSet(order = "007", id = "initAdmin", author = "skieylion", runAlways = true)
    public void initAdmin(UserDao userDao){
        User admin=new User();
        admin.setLogin("admin");
        admin.setName("Админов Админ Админович");
        admin.setPassword("$2y$04$K9YstzpCyK/S5VyL4CXE7uSQGAjghagffInMDs7bH56Jqc.SYk59q");
        Role role=new Role();
        role.setName("ADMIN");
        role.setPermissions(new ArrayList<String>(Arrays.asList(new String[]{Permission.WRITE.get(),Permission.READ.get()})));
        admin.setRole(role);
        userDao.save(admin);
    }
}
