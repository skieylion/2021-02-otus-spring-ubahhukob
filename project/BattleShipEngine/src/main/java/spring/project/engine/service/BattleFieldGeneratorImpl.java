package spring.project.engine.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import spring.project.common.model.BattleField;
import spring.project.common.model.BattleFieldVO;
import spring.project.engine.exception.PositionShipNotFoundException;
import spring.project.engine.model.PlayerDB;
import spring.project.engine.repository.BattleFieldRepository;

@Service
@AllArgsConstructor
public class BattleFieldGeneratorImpl implements BattleFieldGenerator {

    private final BattleFieldBuilder battleFieldBuilder;
    private final BattleFieldRepository battleFieldRepository;
    private final ConverterService converterService;

    @Override
    public PlayerDB generate10(String playerId) throws PositionShipNotFoundException {
        PlayerDB playerDB =battleFieldRepository.findById(playerId).orElseThrow();
        int size = 10;
        BattleField battleField=battleFieldBuilder.create(size,size);
        battleFieldBuilder.addShip(battleField,battleFieldBuilder.getRandomShip(battleField,4));
        battleFieldBuilder.addShip(battleField,battleFieldBuilder.getRandomShip(battleField,3));
        battleFieldBuilder.addShip(battleField,battleFieldBuilder.getRandomShip(battleField,3));
        battleFieldBuilder.addShip(battleField,battleFieldBuilder.getRandomShip(battleField,2));
        battleFieldBuilder.addShip(battleField,battleFieldBuilder.getRandomShip(battleField,2));
        battleFieldBuilder.addShip(battleField,battleFieldBuilder.getRandomShip(battleField,2));
        battleFieldBuilder.addShip(battleField,battleFieldBuilder.getRandomShip(battleField,1));
        battleFieldBuilder.addShip(battleField,battleFieldBuilder.getRandomShip(battleField,1));
        battleFieldBuilder.addShip(battleField,battleFieldBuilder.getRandomShip(battleField,1));
        battleFieldBuilder.addShip(battleField,battleFieldBuilder.getRandomShip(battleField,1));
        BattleFieldVO battleFieldVO=converterService.convert(battleField);
        playerDB.setField(battleFieldVO);
        battleFieldRepository.save(playerDB);
        return playerDB;
    }
}
