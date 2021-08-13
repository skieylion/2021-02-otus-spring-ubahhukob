package spring.project.bot.model;

import lombok.Data;

@Data
public class PlayerVO {
    private String sessionId;
    private String sessionEnemyId;
    private PlayerState state;
    private Boolean active;
    private BattleFieldVO field;
}
