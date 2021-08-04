package spring.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import spring.project.exception.PositionShipNotFoundException;
import spring.project.service.BattleFieldBuilder;
import spring.project.service.BattleFieldGenerator;

@SpringBootApplication
public class App {
    public static void main(String[] args) throws PositionShipNotFoundException {
        ApplicationContext context=SpringApplication.run(App.class);
        BattleFieldGenerator battleFieldGenerator =context.getBean(BattleFieldGenerator.class);
        System.out.println(battleFieldGenerator.generate());
    }
}