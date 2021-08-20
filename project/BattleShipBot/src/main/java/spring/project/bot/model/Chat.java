package spring.project.bot.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import spring.project.common.model.Player;

@Data
@RedisHash(value = "chatPlayer", timeToLive = 86400)
public class Chat {
    @Id
    private Long chatId;
    private Player player;
    private ChatState chatState;
    private Integer userId;
}
