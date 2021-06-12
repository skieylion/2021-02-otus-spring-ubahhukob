package spring.homework.h2.domain;

import javax.persistence.*;

@Entity
@Table(name = "AUTHOR")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "FULLNAME")
    private String fullName;
    @Column(name = "alias")
    private String alias;

    public Author(long id, String fullName, String alias) {
        this.id = id;
        this.fullName = fullName;
        this.alias = alias;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Author(String fullName, String alias) {
        this.fullName = fullName;
        this.alias = alias;
    }

    public Author() {
    }

    public Author(long id) {
        this.id = id;
    }


    public long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAlias() {
        return alias;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", alias='" + alias + '\'' +
                '}';
    }
}
