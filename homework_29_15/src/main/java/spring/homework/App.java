package spring.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import spring.homework.service.TemperatureGeneratorService;

@EnableIntegration
@SpringBootApplication
public class App {
    public static void main( String[] args ) throws Exception {
        ApplicationContext context=SpringApplication.run(App.class,args);
        context.getBean(TemperatureGeneratorService.class).generate();
    }
}