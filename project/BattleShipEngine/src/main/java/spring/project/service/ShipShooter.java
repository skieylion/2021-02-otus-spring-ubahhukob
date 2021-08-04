package spring.project.service;

import spring.project.model.BattleField;
import spring.project.model.FireResult;
import spring.project.model.Point;

public interface ShipShooter {
    FireResult fire(BattleField battleField, Point point);
}
