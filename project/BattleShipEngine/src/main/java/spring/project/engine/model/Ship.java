package spring.project.engine.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import spring.project.common.model.Point;

@Data
@AllArgsConstructor
public class Ship {
    private int size;
    private Point point;
    private DirectionType align;
}
