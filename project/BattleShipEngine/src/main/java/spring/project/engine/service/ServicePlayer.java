package spring.project.engine.service;

import spring.project.common.model.FireResponse;
import spring.project.common.model.Player;
import spring.project.engine.exception.PlayerJoinException;
import spring.project.engine.model.PlayerDB;

public interface ServicePlayer {
    PlayerDB start();
    Player join(String id) throws PlayerJoinException;
    void go(String id) throws PlayerJoinException;
    FireResponse fire(String playerId, int x, int y);
    void close(String playerId);
    PlayerDB get(String playerId);
}
