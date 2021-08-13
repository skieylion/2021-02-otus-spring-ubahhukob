package spring.project.manager.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@RedisHash("player")
public class Player {
    @Id
    private String sessionId;
    private String sessionEnemyId;
    private PlayerState state;
    private Boolean active;
    private BattleFieldVO field;
}
