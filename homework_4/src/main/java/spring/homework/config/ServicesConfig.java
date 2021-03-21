package spring.homework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.homework.services.ServiceConsole;
import spring.homework.services.ServiceIO;
import spring.homework.services.ServiceLocale;
import spring.homework.services.ServiceLocaleImpl;

@Configuration
public class ServicesConfig {

    @Bean
    public ServiceIO serviceConsole(){
        return new ServiceConsole(System.in,System.out);
    }

}