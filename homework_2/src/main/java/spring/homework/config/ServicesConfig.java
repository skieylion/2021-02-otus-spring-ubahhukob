package spring.homework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import spring.homework.services.ServiceConsole;
import spring.homework.services.ServiceIO;

@Configuration
public class ServicesConfig {
    @Bean
    public ServiceIO serviceConsole(){
        return new ServiceConsole(System.in,System.out);
    }
}