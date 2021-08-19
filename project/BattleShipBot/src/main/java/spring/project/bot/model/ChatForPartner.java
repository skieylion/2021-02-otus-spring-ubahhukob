package spring.project.bot.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "chatForPartner", timeToLive = 86400)
@Data
public class ChatForPartner {
    @Id
    private String partnerId;
    private Long chatId;
}
