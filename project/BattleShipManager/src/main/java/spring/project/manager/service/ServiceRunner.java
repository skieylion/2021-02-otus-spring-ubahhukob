package spring.project.manager.service;

import spring.project.engine.exception.PositionShipNotFoundException;
import spring.project.manager.exception.PlayerJoinException;

public interface ServiceRunner {
    void run() throws PlayerJoinException, PositionShipNotFoundException;
}
