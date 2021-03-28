package spring.homework.repositories;

import org.springframework.data.annotation.Persistent;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.homework.domain.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class BookRepositoryImpl implements  CourseRepository{
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void test() {
        //EntityTransaction tx=em.getTransaction();
        //tx.begin();
        Book b=new Book(2L,"asdasd");
        em.persist(b);

        //System.out.println(em.find(Book.class,2L));
        //
        //em.getTransaction().commit();
        //tx.commit();
        //System.out.println(em.find(Book.class,1L));

    }
}
