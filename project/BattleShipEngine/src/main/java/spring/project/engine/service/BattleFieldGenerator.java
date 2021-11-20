package spring.project.engine.service;


import spring.project.engine.exception.PositionShipNotFoundException;
import spring.project.engine.model.PlayerDB;

public interface BattleFieldGenerator {
    PlayerDB generate10(String playerId) throws PositionShipNotFoundException;
}
