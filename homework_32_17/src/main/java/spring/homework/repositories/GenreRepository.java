package spring.homework.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import spring.homework.domain.Genre;

public interface GenreRepository extends PagingAndSortingRepository<Genre,Long> {
}
