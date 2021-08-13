package spring.project.manager.service;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;
import spring.project.engine.exception.PositionShipNotFoundException;
import spring.project.engine.service.BattleFieldBuilderImpl;
import spring.project.engine.service.ShipShooterImpl;
import spring.project.manager.exception.PlayerJoinException;
import spring.project.manager.model.FireResponse;
import spring.project.manager.model.Player;

@Service
@AllArgsConstructor
@Import({BattleFieldBuilderImpl.class, ShipShooterImpl.class})
public class ServiceRunnerImpl implements ServiceRunner{

    private final ServicePlayer servicePlayer;
    private final BattleFieldGenerator battleFieldGenerator;

    @Override
    public void run() throws PlayerJoinException, PositionShipNotFoundException {
        Player player=servicePlayer.start();
        String myId=player.getSessionId();
        String enemyId=player.getSessionEnemyId();
        servicePlayer.join(myId);
        servicePlayer.join(enemyId);
        battleFieldGenerator.generate10(myId);
        battleFieldGenerator.generate10(enemyId);
        servicePlayer.go(myId);
        servicePlayer.go(enemyId);

        FireResponse fireResponse = servicePlayer.fire(myId, 2, 2);
        System.out.println(fireResponse.getFireResult());
        System.out.println(fireResponse.getField());
        System.out.println(fireResponse.getEnemyField());

        fireResponse = servicePlayer.fire(enemyId, 2, 2);
        System.out.println(fireResponse.getFireResult());
        System.out.println(fireResponse.getField());
        System.out.println(fireResponse.getEnemyField());
    }
}
