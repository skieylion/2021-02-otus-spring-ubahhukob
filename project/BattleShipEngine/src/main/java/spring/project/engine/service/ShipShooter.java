package spring.project.engine.service;

import spring.project.common.model.BattleField;
import spring.project.common.model.FireResult;
import spring.project.common.model.Point;

public interface ShipShooter {
    FireResult fire(BattleField battleField, Point point);
}
