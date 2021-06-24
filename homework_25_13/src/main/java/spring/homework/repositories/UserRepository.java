package spring.homework.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.homework.domain.User;

public interface UserRepository extends CrudRepository<User,Long> {
    User findByLogin(String login);
    Boolean existsByLogin(String login);
}