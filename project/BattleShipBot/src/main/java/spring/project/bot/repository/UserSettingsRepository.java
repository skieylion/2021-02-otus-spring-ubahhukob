package spring.project.bot.repository;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;
import spring.project.bot.model.UserSettings;

@Repository
public interface UserSettingsRepository extends KeyValueRepository<UserSettings, Integer> {
}
