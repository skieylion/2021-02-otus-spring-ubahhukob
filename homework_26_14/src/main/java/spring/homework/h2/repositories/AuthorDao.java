package spring.homework.h2.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import spring.homework.h2.domain.Author;

@Repository("authorDaoH2")
public interface AuthorDao extends CrudRepository<Author,Long> {

}
