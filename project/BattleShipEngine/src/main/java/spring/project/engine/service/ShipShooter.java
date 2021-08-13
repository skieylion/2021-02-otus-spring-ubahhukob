package spring.project.engine.service;

import spring.project.engine.model.BattleField;
import spring.project.engine.model.FireResult;
import spring.project.engine.model.Point;

public interface ShipShooter {
    FireResult fire(BattleField battleField, Point point);
}
