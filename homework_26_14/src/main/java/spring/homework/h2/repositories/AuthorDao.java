package spring.homework.h2.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import spring.homework.h2.domain.AuthorH2;

import java.util.List;

@Repository("authorDaoH2")
public interface AuthorDao extends CrudRepository<AuthorH2,Long> {
    List<AuthorH2> findByFullName(String fullName);
}
