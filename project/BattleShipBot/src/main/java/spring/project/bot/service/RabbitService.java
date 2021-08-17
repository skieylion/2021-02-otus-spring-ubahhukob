package spring.project.bot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import spring.project.common.model.FireResponse;
import spring.project.common.model.Player;
import spring.project.common.model.Point;

public interface RabbitService {
    Player start() throws JsonProcessingException;
    Player join(String playerId) throws JsonProcessingException;
    Player generate(String playerId) throws JsonProcessingException;
    FireResponse fire(String playerId, Point point) throws JsonProcessingException;
    void close(String playerId);
}
