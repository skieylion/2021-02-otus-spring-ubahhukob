package spring.project.manager.model;

import lombok.Data;
import spring.project.engine.model.BattleField;
import spring.project.engine.model.FireResult;

@Data
public class FireResponse {
    private FireResult fireResult;
    private BattleField field;
    private BattleField enemyField;
}
