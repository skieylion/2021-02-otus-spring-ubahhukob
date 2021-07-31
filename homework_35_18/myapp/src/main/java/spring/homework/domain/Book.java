package spring.homework.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "Book")
public class Book {
    @Id
    private String id;
    private String name;

    private Author author;
    private Genre genre;
    @DBRef
    private List<Comment> comments;

    public Book(String id, String name, Author author, Genre genre, List<Comment> comments) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.comments = comments;
    }

    public Book(String id, String name, Author author, Genre genre) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
    }

    public Book(String name, Author author, Genre genre, List<Comment> comments) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.comments = comments;
    }

    public Book(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Book(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author=" + author +
                ", genre=" + genre +
                '}';
    }
}
