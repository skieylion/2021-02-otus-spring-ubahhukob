package spring.project.engine.model;

import lombok.Data;
import spring.project.common.model.BattleField;

@Data
public class Fire {
    private int row;
    private int column;
    private BattleField battleField;
}
