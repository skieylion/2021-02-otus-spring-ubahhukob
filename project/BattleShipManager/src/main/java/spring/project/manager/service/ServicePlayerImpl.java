package spring.project.manager.service;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import spring.project.engine.model.BattleField;
import spring.project.engine.model.FireResult;
import spring.project.engine.model.Point;
import spring.project.engine.service.ShipShooter;
import spring.project.manager.exception.PlayerJoinException;
import spring.project.manager.model.BattleFieldVO;
import spring.project.manager.model.FireResponse;
import spring.project.manager.model.Player;
import spring.project.manager.model.PlayerState;
import spring.project.manager.repository.BattleFieldRepository;

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
    public void go(String id) throws PlayerJoinException {
        Player player=battleFieldRepository.findById(id).orElseThrow(PlayerJoinException::new);
        player.setState(PlayerState.PLAYING);
        battleFieldRepository.save(player);
    }

    @Override
    public FireResponse fire(String playerId, int x, int y) {
        Player player=battleFieldRepository.findById(playerId).orElseThrow();
        String enemyId=player.getSessionEnemyId();
        Player enemy=battleFieldRepository.findById(enemyId).orElseThrow();
        BattleFieldVO battleFieldVO=enemy.getField();
        BattleField battleField=converterService.convert(battleFieldVO);
        FireResult fireResult=shipShooter.fire(battleField,new Point(x,y));
        enemy.setField(converterService.convert(battleField));
        battleFieldRepository.save(enemy);
        FireResponse fireResponse=new FireResponse();
        fireResponse.setFireResult(fireResult);
        fireResponse.setField(converterService.convert(player.getField()));
        fireResponse.setEnemyField(converterService.convertEnemyField(converterService.convert(enemy.getField())));
        return fireResponse;
    }

    @Override
    public void join(String id) throws PlayerJoinException {
        Player player=battleFieldRepository.findById(id).orElseThrow(PlayerJoinException::new);
        player.setState(PlayerState.BEGINNING);
        battleFieldRepository.save(player);
    }

    @Override
    public Player start() {
        String myId =getId();
        String enemyId = getId();

        Player player=new Player();
        player.setSessionId(myId);
        player.setSessionEnemyId(enemyId);
        player.setState(PlayerState.DEFINING);
        player = battleFieldRepository.save(player);

        Player enemyPlayer=new Player();
        enemyPlayer.setSessionId(enemyId);
        enemyPlayer.setSessionEnemyId(myId);
        enemyPlayer.setState(PlayerState.DEFINING);
        battleFieldRepository.save(enemyPlayer);

        return player;
    }
}
