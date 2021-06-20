package spring.homework.h2.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import spring.homework.h2.domain.BookH2;

import java.util.List;

@Repository("bookDaoH2")
public interface BookRepositoryH2 extends CrudRepository<BookH2,Long> {
    @EntityGraph(attributePaths = {"authorH2","genreH2"})
    List<BookH2> findAll();
}
