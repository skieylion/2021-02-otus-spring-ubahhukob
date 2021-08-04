package spring.project.service;

import spring.project.exception.PositionShipNotFoundException;
import spring.project.model.BattleField;

public interface BattleFieldGenerator {
    BattleField generate() throws PositionShipNotFoundException;
}
