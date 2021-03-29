package spring.homework.domain;

import javax.persistence.*;

@Entity(name = "BOOKS")
@Table(name = "BOOKS")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String name;

    @OneToOne(targetEntity = Author.class)
    @JoinColumn(name = "author_id")
    private Author author;
    @OneToOne(targetEntity = Genre.class)
    @JoinColumn(name = "genre_id")
    private Genre genre;
    @OneToOne(targetEntity = Comment.class)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Book(long id, String name, Author author, Genre genre, Comment comment) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.comment = comment;
    }

    public Book(long id, String name, Author author, Genre genre) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;

    }

    public Book(String name, Author author, Genre genre, Comment comment) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.comment = comment;
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
                ", comment=" + comment +
                '}';
    }
}
