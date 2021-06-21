package spring.homework.h2.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import spring.homework.h2.domain.CommentH2;

@Repository("commentDaoH2")
public interface CommentRepositoryH2 extends CrudRepository<CommentH2,Long> {
}
