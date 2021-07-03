package spring.homework.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import spring.homework.domain.Book;

import java.util.List;

public interface BookRepository extends CrudRepository<Book,Long> {

    @EntityGraph(attributePaths = {"author","genre"})
    List<Book> findAll();

    Book findById(long id);

    Book save(Book book);

    void deleteById(long id);
}