package spring.homework;

import org.springframework.context.annotation.*;
import org.springframework.test.context.TestPropertySource;
import spring.homework.services.*;
import spring.homework.config.ServicesConfig;
import spring.homework.services.ServiceSurveyImpl;


@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")

public class App 
{

    public static void main( String[] args ) throws Exception {
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(App.class);

        ServiceUser serviceUser=context.getBean("serviceUserImpl", ServiceUser.class);
        serviceUser.inputUserData();

        ServiceSurvey serviceSurvey=context.getBean("serviceSurveyImpl", ServiceSurvey.class);
        serviceSurvey.test();


        context.close();

    }


}
