package spring.homework;

import org.springframework.context.annotation.*;
import org.springframework.test.context.TestPropertySource;
import spring.homework.services.ServiceFormImpl;
import spring.homework.config.ServicesConfig;


@Configuration
@Import(ServicesConfig.class)
@ComponentScan
@PropertySource("classpath:application.properties")

public class App 
{

    public static void main( String[] args ) throws Exception {
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(App.class);

        ServiceFormImpl sf=context.getBean("serviceForm", ServiceFormImpl.class);
        sf.input();
        sf.test();
        sf.end();


        context.close();

    }


}
