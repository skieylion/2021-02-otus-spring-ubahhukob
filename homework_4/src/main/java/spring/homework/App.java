package spring.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import spring.homework.services.ServiceSurveyImpl;

@SpringBootApplication
@Configuration
@ComponentScan
public class App 
{

    public static void main( String[] args ) throws Exception {
        ApplicationContext context=SpringApplication.run(App.class, args);

        ServiceSurveyImpl sf=context.getBean("serviceSurveyImpl", ServiceSurveyImpl.class);
        sf.test();
    }


}