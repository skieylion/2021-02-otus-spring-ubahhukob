package spring.project.manager.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import spring.project.engine.exception.PositionShipNotFoundException;
import spring.project.engine.model.BattleField;
import spring.project.engine.service.BattleFieldBuilder;
import spring.project.manager.model.BattleFieldVO;
import spring.project.manager.model.Player;
import spring.project.manager.repository.BattleFieldRepository;

@Service
@AllArgsConstructor
public class BattleFieldGeneratorImpl implements BattleFieldGenerator {

    private final BattleFieldBuilder battleFieldBuilder;
    private final BattleFieldRepository battleFieldRepository;
    private final ConverterService converterService;

    @Override
    public Player generate10(String playerId) throws PositionShipNotFoundException {
        Player player=battleFieldRepository.findById(playerId).orElseThrow();
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
        player.setField(battleFieldVO);
        battleFieldRepository.save(player);
        return player;
    }
}
