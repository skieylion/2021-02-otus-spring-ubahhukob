package spring.project.common.model;

import lombok.Data;

@Data
public class Player {
    private String id;
    protected String enemyId;
    protected BattleFieldVO field;
}
