package spring.project.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import spring.project.exception.PositionShipNotFoundException;
import spring.project.model.BattleField;
import spring.project.model.Ship;

@Service
@AllArgsConstructor
public class BattleFieldGeneratorImpl implements BattleFieldGenerator{

    private final BattleFieldBuilder battleFieldBuilder;

    @Override
    public BattleField generate() throws PositionShipNotFoundException {
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

        return battleField;
    }
}
