package spring.homework.h2.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "BOOK")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String name;

    @ManyToOne(targetEntity = Author.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id")
    private Author author;
    @ManyToOne(targetEntity = Genre.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(targetEntity = Comment.class,mappedBy = "book",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Comment> comments;

    public List<Comment> getComments() {
        if(comments==null) return new ArrayList<>();
        return comments;
    }

    public void setComment(List<Comment> comments) {
        this.comments = comments;
    }

    public Book(long id, String name, Author author, Genre genre, List<Comment> comments) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.comments = comments;
    }

    public Book(long id, String name, Author author, Genre genre) {
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

    public Book() {
    }

    public Book(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Book(long id) {
        this.id = id;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Genre getGenre() {
        return genre;
    }

    public Book(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
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
