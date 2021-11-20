package spring.project.bot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import spring.project.common.model.FireResponse;
import spring.project.common.model.Player;
import spring.project.common.model.Point;

public interface RabbitService {
    Player startBattle();

    Player joinToBattle(String playerId);

    Player generateField(String playerId);

    FireResponse fireOnShip(String playerId, Point point);

    void closeBattle(String playerId);
}
