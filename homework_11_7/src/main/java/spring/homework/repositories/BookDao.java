package spring.homework.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.homework.domain.Book;

import java.util.List;

public interface BookDao extends CrudRepository<Book,Long> {

}
