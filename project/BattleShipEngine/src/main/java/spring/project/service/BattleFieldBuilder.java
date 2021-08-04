package spring.project.service;

import spring.project.exception.PositionShipNotFoundException;
import spring.project.model.AlignType;
import spring.project.model.BattleField;
import spring.project.model.Point;
import spring.project.model.Ship;


public interface BattleFieldBuilder {
    BattleField create(int row,int column);
    void addShip(BattleField battleField, Ship ship) throws PositionShipNotFoundException;
    Ship getRandomShip(BattleField battleField,int sizeShip) throws PositionShipNotFoundException;
}
