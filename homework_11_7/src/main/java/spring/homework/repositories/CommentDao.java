package spring.homework.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.homework.domain.Comment;

public interface CommentDao extends CrudRepository<Comment,Long> {
}
