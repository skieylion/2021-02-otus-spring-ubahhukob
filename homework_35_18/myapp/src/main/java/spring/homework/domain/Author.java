package spring.homework.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "Author")
public class Author {
    @Id
    private String id;
    private String fullName;
    private String alias;

    public Author(String id, String fullName, String alias) {
        this.id = id;
        this.fullName = fullName;
        this.alias = alias;
    }

    public Author(String fullName, String alias) {
        this.fullName = fullName;
        this.alias = alias;
    }
}
