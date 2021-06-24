package spring.homework.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.homework.domain.Comment;

public interface CommentRepository extends CrudRepository<Comment,Long> {
    void deleteByBookId(long id);
}
