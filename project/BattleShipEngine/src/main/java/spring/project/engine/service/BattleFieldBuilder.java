package spring.project.engine.service;

import spring.project.common.model.BattleField;
import spring.project.engine.exception.PositionShipNotFoundException;
import spring.project.engine.model.Ship;


public interface BattleFieldBuilder {
    BattleField create(int row, int column);
    void addShip(BattleField battleField, Ship ship) throws PositionShipNotFoundException;
    Ship getRandomShip(BattleField battleField, int sizeShip) throws PositionShipNotFoundException;
}
