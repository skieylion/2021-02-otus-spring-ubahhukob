package spring.homework.h2.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import spring.homework.h2.domain.Genre;

@Repository("genreDaoH2")
public interface GenreDao extends CrudRepository<Genre,Long> {
}
