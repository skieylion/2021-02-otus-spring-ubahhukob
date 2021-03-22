package spring.homework.domain;

public class Author {
    private long id;
    private String fullName;
    private String alias;

    public Author(long id, String fullName, String alias) {
        this.id = id;
        this.fullName = fullName;
        this.alias = alias;
    }

    public Author(String fullName, String alias) {
        this.fullName = fullName;
        this.alias = alias;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAlias() {
        return alias;
    }
}
