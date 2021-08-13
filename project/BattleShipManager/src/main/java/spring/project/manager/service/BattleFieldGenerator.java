package spring.project.manager.service;


import spring.project.engine.exception.PositionShipNotFoundException;
import spring.project.manager.model.Player;

public interface BattleFieldGenerator {
    Player generate10(String playerId) throws PositionShipNotFoundException;
}
