package spring.project.bot.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@RedisHash(value = "userSettings", timeToLive = 2592000)
public class UserSettings {
    @Id
    private Integer userId;
    private String locale;
}
