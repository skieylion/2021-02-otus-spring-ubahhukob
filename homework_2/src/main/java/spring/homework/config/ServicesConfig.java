package spring.homework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import spring.homework.services.ServiceConsole;

@Configuration
public class ServicesConfig {
    @Bean
    public ServiceConsole getConsole(){
        return new ServiceConsole(System.in,System.out);
    }
}
