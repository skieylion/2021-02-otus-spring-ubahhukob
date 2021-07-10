package spring.homework.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import spring.homework.domain.Author;

//@RepositoryRestResource(path = "Author")
public interface AuthorRepository extends PagingAndSortingRepository<Author,Long> {

}
