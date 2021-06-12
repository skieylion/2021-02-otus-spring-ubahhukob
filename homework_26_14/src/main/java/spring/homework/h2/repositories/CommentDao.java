package spring.homework.h2.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import spring.homework.h2.domain.Comment;

@Repository("commentDaoH2")
public interface CommentDao extends CrudRepository<Comment,Long> {
}
