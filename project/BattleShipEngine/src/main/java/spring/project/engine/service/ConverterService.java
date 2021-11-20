package spring.project.engine.service;

import spring.project.common.model.BattleField;
import spring.project.common.model.BattleFieldVO;

public interface ConverterService {
    BattleField convert(BattleFieldVO battleFieldVO);
    BattleFieldVO convert(BattleField battleField);
    BattleField convertEnemyField(BattleField battleField);
}
