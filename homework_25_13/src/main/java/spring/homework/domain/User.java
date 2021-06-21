package spring.homework.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "User")
public class User {
    @Id
    private String id;
    private String login;
    private String name;
    private String password;
    private Role role;
}
