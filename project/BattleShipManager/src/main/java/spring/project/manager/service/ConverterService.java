package spring.project.manager.service;

import spring.project.engine.model.BattleField;
import spring.project.manager.model.BattleFieldVO;

public interface ConverterService {
    BattleField convert(BattleFieldVO battleFieldVO);
    BattleFieldVO convert(BattleField battleField);
    BattleField convertEnemyField(BattleField battleField);
}
