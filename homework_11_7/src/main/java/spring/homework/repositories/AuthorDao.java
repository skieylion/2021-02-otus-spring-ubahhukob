package spring.homework.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.homework.domain.Author;

public interface AuthorDao extends CrudRepository<Author,Long> {

}
