package spring.homework.domain;

import javax.persistence.*;

@Entity(name = "COMMENTS")
@Table(name = "COMMENTS")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "description")
    private String description;

    public Comment() {
    }

    public Comment(long id, String description) {
        this.id = id;
        this.description = description;
    }

    public Comment(long id) {
        this.id = id;
    }

    public Comment(String description) {
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

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
