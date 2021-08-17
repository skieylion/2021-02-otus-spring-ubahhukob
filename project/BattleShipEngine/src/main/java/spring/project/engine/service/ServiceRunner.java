package spring.project.engine.service;

import spring.project.engine.exception.PositionShipNotFoundException;
import spring.project.engine.exception.PlayerJoinException;

public interface ServiceRunner {
    void run() throws PlayerJoinException, PositionShipNotFoundException;
}
