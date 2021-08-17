package spring.project.engine.service;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import spring.project.common.model.*;
import spring.project.engine.exception.PlayerJoinException;
import spring.project.engine.model.PlayerDB;
import spring.project.engine.repository.BattleFieldRepository;

@Service
@AllArgsConstructor
public class ServicePlayerImpl implements ServicePlayer {

    private final BattleFieldRepository battleFieldRepository;
    private final ConverterService converterService;
    private final ShipShooter shipShooter;

    private String getId(){
        return RandomStringUtils.random(4,"BCDFGHJLKMNPQRSTVWXZ123456789");
    }

    @Override
    public void close(String playerId) {
        battleFieldRepository.deleteById(playerId);
    }

    @Override
    public PlayerDB get(String playerId) {
        return battleFieldRepository.findById(playerId).orElseThrow();
    }

    @Override
    public void go(String id) throws PlayerJoinException {
        PlayerDB playerDB =battleFieldRepository.findById(id).orElseThrow(PlayerJoinException::new);
        battleFieldRepository.save(playerDB);
    }

    @Override
    public FireResponse fire(String playerId, int x, int y) {
        System.out.println(playerId);
        //System.out.println(playerId);
        PlayerDB playerDB =battleFieldRepository.findById(playerId).orElseThrow();
        String enemyId= playerDB.getEnemyId();
        PlayerDB enemy=battleFieldRepository.findById(enemyId).orElseThrow();
        BattleFieldVO battleFieldVO=enemy.getField();
        BattleField battleField=converterService.convert(battleFieldVO);
        FireResult fireResult=shipShooter.fire(battleField,new Point(x,y));
        enemy.setField(converterService.convert(battleField));
        battleFieldRepository.save(enemy);
        FireResponse fireResponse=new FireResponse();
        fireResponse.setFireResult(fireResult);
        fireResponse.setField(converterService.convert(playerDB.getField()));
        fireResponse.setEnemyField(converterService.convertEnemyField(converterService.convert(enemy.getField())));
        return fireResponse;
    }

    @Override
    public Player join(String id) throws PlayerJoinException {
        PlayerDB playerDB =battleFieldRepository.findById(id).orElseThrow(PlayerJoinException::new);
        battleFieldRepository.save(playerDB);
        return playerDB;
    }

    @Override
    public PlayerDB start() {
        String myId =getId();
        String enemyId = getId();

        PlayerDB playerDB =new PlayerDB();
        playerDB.setId(myId);
        playerDB.setEnemyId(enemyId);
        playerDB = battleFieldRepository.save(playerDB);

        PlayerDB enemyPlayerDB =new PlayerDB();
        enemyPlayerDB.setId(enemyId);
        enemyPlayerDB.setEnemyId(playerDB.getId());
        battleFieldRepository.save(enemyPlayerDB);

        return playerDB;
    }
}
