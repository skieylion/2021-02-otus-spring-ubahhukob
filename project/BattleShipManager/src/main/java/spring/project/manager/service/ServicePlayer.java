package spring.project.manager.service;

import spring.project.manager.exception.PlayerJoinException;
import spring.project.manager.model.FireResponse;
import spring.project.manager.model.Player;

public interface ServicePlayer {
    Player start();
    void join(String id) throws PlayerJoinException;
    void go(String id) throws PlayerJoinException;
    FireResponse fire(String playerId, int x, int y);
    void close(String playerId);
}
