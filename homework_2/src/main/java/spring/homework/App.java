package spring.homework;

import org.springframework.context.annotation.*;
import org.springframework.test.context.TestPropertySource;
import spring.homework.services.ServiceSurveyImpl;
import spring.homework.config.ServicesConfig;
import spring.homework.services.ServiceSurveyImpl;


@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")

public class App 
{

    public static void main( String[] args ) throws Exception {
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(App.class);

        ServiceSurveyImpl sf=context.getBean("serviceSurveyImpl", ServiceSurveyImpl.class);
        sf.test();


        context.close();

    }


}
