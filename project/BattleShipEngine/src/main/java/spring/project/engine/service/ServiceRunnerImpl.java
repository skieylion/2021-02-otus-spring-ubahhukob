package spring.project.engine.service;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;
import spring.project.common.model.FireResponse;
import spring.project.engine.exception.PositionShipNotFoundException;
import spring.project.engine.exception.PlayerJoinException;
import spring.project.engine.model.PlayerDB;

@Service
@AllArgsConstructor
@Import({BattleFieldBuilderImpl.class, ShipShooterImpl.class})
public class ServiceRunnerImpl implements ServiceRunner{

    private final ServicePlayer servicePlayer;
    private final BattleFieldGenerator battleFieldGenerator;

    @Override
    public void run() throws PlayerJoinException, PositionShipNotFoundException {
        PlayerDB playerDB =servicePlayer.start();
        String myId= playerDB.getId();
        String enemyId= playerDB.getEnemyId();
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
