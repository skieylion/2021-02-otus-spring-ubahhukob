package spring.homework.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.homework.domain.Author;
import spring.homework.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class CommentDaoImpl implements CommentDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Comment read(long id) {
        Comment comment=em.find(Comment.class,id);
        return comment;
    }

    @Override
    @Transactional
    public long save(Comment comment) {
        if(comment.getId()!=0){
            em.merge(comment);
        } else {
            em.persist(comment);
            return comment.getId();
        }

        return 0;
    }

    @Override
    @Transactional
    public void delete(long id) {
        Comment comment=em.merge(new Comment(id));
        em.remove(comment);
    }
}
