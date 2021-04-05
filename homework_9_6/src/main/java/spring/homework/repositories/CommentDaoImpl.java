package spring.homework.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.homework.domain.Comment;
import spring.homework.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
    private void update(Comment comment) {
        em.merge(comment);
    }
    private long create(Comment comment) {
        em.persist(comment);
        return comment.getId();
    }

    @Override
    public long save(Comment comment) {
        long id = comment.getId();
        if (id != 0) {
            update(comment);
        } else {
            return create(comment);
        }
        return id;
    }

    @Override
    public void delete(long id) {
        Query query = em.createQuery("delete Comment e where e.id=:id");
        query.setParameter("id",id);
        query.executeUpdate();
    }
}
