package spring.project.engine.service;

import spring.project.common.model.FireResponse;
import spring.project.common.model.Player;
import spring.project.engine.exception.PlayerJoinException;
import spring.project.engine.model.PlayerDB;

public interface ServicePlayer {
    PlayerDB startBattle();
    Player joinToBattle(String id) throws PlayerJoinException;
    void goToBattle(String id) throws PlayerJoinException;
    FireResponse fireToBattleField(String playerId, int x, int y);
    void closeBattle(String playerId);
}
