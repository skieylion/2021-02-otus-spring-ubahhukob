package spring.homework.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "Comment")
public class Comment {
    @Id
    private String id;
    private String description;

    @JsonIgnore
    @DBRef
    private Book book;

    public Comment(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public Comment(String description) {
        this.description = description;
    }

}
