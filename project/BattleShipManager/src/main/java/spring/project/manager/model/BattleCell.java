package spring.project.manager.model;

import lombok.Data;
import spring.project.engine.model.CellType;
import spring.project.engine.model.Point;

@Data
public class BattleCell {
    private Point point;
    private CellType cell;
}
