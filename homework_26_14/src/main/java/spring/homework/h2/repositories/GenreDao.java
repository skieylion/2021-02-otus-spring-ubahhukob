package spring.homework.h2.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import spring.homework.domain.Genre;
import spring.homework.h2.domain.BookH2;
import spring.homework.h2.domain.GenreH2;

import java.util.List;

@Repository("genreDaoH2")
public interface GenreDao extends CrudRepository<GenreH2,Long> {
    List<GenreH2> findByName(String name);
}
