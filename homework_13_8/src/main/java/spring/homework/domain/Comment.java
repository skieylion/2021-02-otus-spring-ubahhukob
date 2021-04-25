package spring.homework.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "Comment")
public class Comment {
    @Id
    private String id;
    private String description;
    private Book book;

    public Comment(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public Comment(String description) {
        this.description = description;
    }

}
