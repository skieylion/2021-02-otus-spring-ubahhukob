package spring.homework.domain;

public class Book {
    private long id;
    private String name;
    private long authorId;
    private long genreId;

    public Book(long id, String name, long authorId, long genreId) {
        this.id = id;
        this.name = name;
        this.authorId = authorId;
        this.genreId = genreId;
    }

    public Book(String name, long authorId, long genreId) {
        this.name = name;
        this.authorId = authorId;
        this.genreId = genreId;
    }

    public Book(long id, String name) {
        this.id = id;
        this.name = name;
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

    public long getAuthorId() {
        return authorId;
    }

    public long getGenreId() {
        return genreId;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
