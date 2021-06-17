package spring.homework.h2.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "BOOK")
public class BookH2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String name;

    @ManyToOne(targetEntity = AuthorH2.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id")
    private AuthorH2 authorH2;
    @ManyToOne(targetEntity = GenreH2.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "genre_id")
    private GenreH2 genreH2;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(targetEntity = CommentH2.class,mappedBy = "bookH2",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<CommentH2> commentH2s;

    public List<CommentH2> getComments() {
        if(commentH2s ==null) return new ArrayList<>();
        return commentH2s;
    }

    public void setComment(List<CommentH2> commentH2s) {
        this.commentH2s = commentH2s;
    }

    public BookH2(long id, String name, AuthorH2 authorH2, GenreH2 genreH2, List<CommentH2> commentH2s) {
        this.id = id;
        this.name = name;
        this.authorH2 = authorH2;
        this.genreH2 = genreH2;
        this.commentH2s = commentH2s;
    }

    public BookH2(long id, String name, AuthorH2 authorH2, GenreH2 genreH2) {
        this.id = id;
        this.name = name;
        this.authorH2 = authorH2;
        this.genreH2 = genreH2;

    }

    public BookH2(String name, AuthorH2 authorH2, GenreH2 genreH2, List<CommentH2> commentH2s) {
        this.name = name;
        this.authorH2 = authorH2;
        this.genreH2 = genreH2;
        this.commentH2s = commentH2s;
    }

    public BookH2() {
    }

    public BookH2(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public BookH2(long id) {
        this.id = id;
    }

    public AuthorH2 getAuthor() {
        return authorH2;
    }

    public void setAuthor(AuthorH2 authorH2) {
        this.authorH2 = authorH2;
    }

    public void setGenre(GenreH2 genreH2) {
        this.genreH2 = genreH2;
    }

    public GenreH2 getGenre() {
        return genreH2;
    }

    public BookH2(String name) {
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
                ", author=" + authorH2 +
                ", genre=" + genreH2 +
                '}';
    }
}
