package spring.project.common.model;

import lombok.Data;

@Data
public class FireResponse {
    private FireResult fireResult;
    private BattleField field;
    private BattleField enemyField;
}
