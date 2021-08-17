package spring.project.engine.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import spring.project.common.model.Player;

@EqualsAndHashCode(callSuper = true)
@RedisHash(value = "player",timeToLive = 86400)
@Data
public class PlayerDB extends Player {
    @Id
    private String id;

    public PlayerDB() {
        super();
    }
}
