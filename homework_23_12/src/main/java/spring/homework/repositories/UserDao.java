package spring.homework.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import spring.homework.domain.User;

public interface UserDao extends MongoRepository<User,String> {
    User findByLogin(String login);
    Boolean existsByLogin(String login);
}
