package spring.homework.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import spring.homework.domain.Author;

public interface AuthorRepository extends PagingAndSortingRepository<Author,Long> {

}
