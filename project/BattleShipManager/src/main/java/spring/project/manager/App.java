package spring.project.manager;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import spring.project.engine.exception.PositionShipNotFoundException;
import spring.project.engine.service.BattleFieldBuilder;
import spring.project.engine.service.BattleFieldBuilderImpl;
import spring.project.manager.exception.PlayerJoinException;
import spring.project.manager.model.FireResponse;
import spring.project.manager.model.Player;
import spring.project.manager.service.BattleFieldGenerator;
import spring.project.manager.service.ServicePlayer;
import spring.project.manager.service.ServiceRunner;

@SpringBootApplication
@EnableRabbit
public class App {

    public static void main(String[] args) throws PlayerJoinException, PositionShipNotFoundException {
        ApplicationContext context=SpringApplication.run(App.class);
        //ServiceRunner serviceRunner=context.getBean(ServiceRunner.class);
        //serviceRunner.run();
    }
}
