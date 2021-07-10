package spring.homework.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import spring.homework.domain.Comment;

public interface CommentRepository extends PagingAndSortingRepository<Comment,Long> {
    void deleteByBookId(long id);
}
