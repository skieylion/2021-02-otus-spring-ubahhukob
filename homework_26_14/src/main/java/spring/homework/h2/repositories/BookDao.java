package spring.homework.h2.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import spring.homework.h2.domain.Book;

import java.util.List;

@Repository("bookDaoH2")
public interface BookDao extends CrudRepository<Book,Long> {
    @EntityGraph(attributePaths = {"author","genre"})
    List<Book> findAll();
}
