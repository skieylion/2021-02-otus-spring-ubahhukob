package spring.homework.h2.domain;

import javax.persistence.*;

@Entity
@Table(name = "GENRE")
public class GenreH2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String name;

    public GenreH2() {
    }

    public GenreH2(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public GenreH2(String genreName) {
        this.name = genreName;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public GenreH2(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
