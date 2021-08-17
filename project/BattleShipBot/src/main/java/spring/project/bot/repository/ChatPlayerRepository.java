package spring.project.bot.repository;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;
import spring.project.bot.model.Chat;

@Repository
public interface ChatPlayerRepository extends KeyValueRepository<Chat,Long> {
}
