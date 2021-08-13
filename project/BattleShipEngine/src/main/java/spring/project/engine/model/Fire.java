package spring.project.engine.model;

import lombok.Data;

@Data
public class Fire {
    private int row;
    private int column;
    private BattleField battleField;
}
