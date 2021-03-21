package spring.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import spring.homework.services.ServiceTest;
import spring.homework.services.TestRunner;

@SpringBootApplication
@EnableAutoConfiguration
@Configuration
@ComponentScan
public class App 
{
    public static void main( String[] args ) throws Exception {
        ApplicationContext context=SpringApplication.run(App.class, args);
        TestRunner testRunner=context.getBean(TestRunner.class);
        testRunner.run();
    }


}