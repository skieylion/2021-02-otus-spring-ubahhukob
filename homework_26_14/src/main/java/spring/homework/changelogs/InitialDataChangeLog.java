package spring.homework.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import spring.homework.domain.*;
import spring.homework.repositories.*;

import java.util.ArrayList;
import java.util.List;

@ChangeLog(order = "001")
public class InitialDataChangeLog {

    private Genre genre;
    private Author author;
    private Comment comment;
    private Book book;

    @ChangeSet(order = "000", id = "dropDB", author = "skieylion", runAlways = true)
    public void dropDB(MongoDatabase database){
        database.drop();
    }

    @ChangeSet(order = "001", id = "initGenre", author = "skieylion", runAlways = true)
    public void initGenre(GenreRepository repository){
        genre = repository.save(new Genre("6081cc3a3d6d754095f46033","Роман"));
        repository.save(new Genre("6081cc3a3d6d754095f46031","Фантастика"));
    }

    @ChangeSet(order = "002", id = "initAuthor", author = "skieylion", runAlways = true)
    public void initAuthor(AuthorRepository repository){
        author =repository.save(new Author("6081cc3a3d6d754095f46027","Алексей Максимович Пешков","М. Горький"));
    }

    @ChangeSet(order = "003", id = "initComment", author = "skieylion", runAlways = true)
    public void initComment(CommentRepository repository){
        comment = new Comment("Отличная книга");
        comment =repository.save(comment);

        repository.save(new Comment("1081cc3a3d6d754095f46021","Плохая книга"));
        repository.save(new Comment("2081cc3a3d6d754095f46022","Прекрасная книга"));
        repository.save(new Comment("3081cc3a3d6d754095f46023","Не знаю"));
    }
    @ChangeSet(order = "004", id = "initBook", author = "skieylion", runAlways = true)
    public void initBook(BookRepository bookRepository){
        List<Comment> commentList =new ArrayList<>();
        commentList.add(comment);
        book =new Book("2222cc3a3d6d754095f46023","Мать", author, genre, commentList);
        book = bookRepository.save(book);

        Book book2 =new Book("3333cc3a3d6d754095f46023","Мать2", author, genre, commentList);
        bookRepository.save(book2);

        Book book3 =new Book("4444cc3a3d6d754095f46023","Мать3", author, genre, commentList);
        bookRepository.save(book3);
    }
    @ChangeSet(order = "005", id = "bookMatchToComment", author = "skieylion", runAlways = true)
    public void bookMatch(CommentRepository repository){
        comment.setBook(book);
        comment =repository.save(comment);
    }
}
