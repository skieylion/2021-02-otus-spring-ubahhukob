package spring.homework.h2.domain;

import javax.persistence.*;

@Entity
@Table(name = "COMMENT")
public class CommentH2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "description")
    private String description;

    @ManyToOne(targetEntity = BookH2.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private BookH2 bookH2;


    public CommentH2() {
    }

    public CommentH2(long id, String description) {
        this.id = id;
        this.description = description;
    }

    public CommentH2(long id) {
        this.id = id;
    }

    public CommentH2(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BookH2 getBook() {
        return bookH2;
    }

    public void setBook(BookH2 bookH2) {
        this.bookH2 = bookH2;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", book=" + bookH2 +
                '}';
    }
}
