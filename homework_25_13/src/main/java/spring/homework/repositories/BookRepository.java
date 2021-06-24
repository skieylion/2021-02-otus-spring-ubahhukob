package spring.homework.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import spring.homework.domain.Book;

import java.util.List;

public interface BookRepository extends CrudRepository<Book,Long> {

    @EntityGraph(attributePaths = {"author","genre"})
    @PostFilter("hasPermission(filterObject, 'READ')")
    List<Book> findAll();

    @PostAuthorize("hasPermission(returnObject, 'READ')")
    Book findById(long id);

    @PreAuthorize("hasPermission(#book, 'WRITE')")
    Book save(@Param("book") Book book);

    @PreAuthorize("hasPermission(returnObject, 'WRITE')")
    void deleteById(long id);
}