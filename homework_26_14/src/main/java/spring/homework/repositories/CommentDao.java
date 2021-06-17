package spring.homework.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import spring.homework.domain.Comment;

public interface CommentDao extends MongoRepository<Comment,String> {
    void deleteByBookId(String id);
}
