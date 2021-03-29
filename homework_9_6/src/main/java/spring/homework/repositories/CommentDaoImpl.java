package spring.homework.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.homework.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class CommentDaoImpl implements CommentDao {

    @PersistenceContext
    private EntityManager em;

    public CommentDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Comment read(long id) {
        return em.find(Comment.class, id);
    }

    @Override
    @Transactional
    public long save(Comment comment) {
        if (comment.getId() != 0) {
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
        Comment comment = em.merge(new Comment(id));
        em.remove(comment);
    }
}
