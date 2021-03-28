package spring.homework.repositories;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class CourseRepositoryImpl implements  CourseRepository{
    @PersistenceContext
    private EntityManager em;

    public void test(){
        Query query=em.createQuery("select e from Book e");
        System.out.println(query.getResultList());
    }
}
