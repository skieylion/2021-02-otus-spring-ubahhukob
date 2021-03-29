package spring.homework.repositories;

import spring.homework.domain.Comment;

public interface CommentDao {
    Comment read(long id);

    long save(Comment author);

    void delete(long id);
}
