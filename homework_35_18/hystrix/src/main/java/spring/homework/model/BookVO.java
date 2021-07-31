package spring.homework.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class BookVO {
    private String id;
    private String name;
    private AuthorVO author;
    private GenreVO genre;

    private List<CommentVO> comments;
}
