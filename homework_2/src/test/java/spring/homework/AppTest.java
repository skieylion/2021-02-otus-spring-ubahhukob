package spring.homework;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import spring.homework.config.ServicesConfig;
import spring.homework.dao.TestCsvDAO;
import spring.homework.domain.UserInput;
import spring.homework.services.ServiceFormImpl;
import spring.homework.services.TestData;
import spring.homework.services.TestDataImpl;


import java.io.*;
import java.util.Properties;

/**
 * Unit test for simple App.
 */
@Configuration
@Import(ServicesConfig.class)
@ComponentScan
@TestPropertySource("classpath:test.properties")
@ContextConfiguration
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */


    @Test
    public void shouldAnswerWithTrue() throws Exception {
 
        TestData td=new TestDataImpl();
        td.pushData();

        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(AppTest.class);

        ServiceFormImpl sf=context.getBean("serviceForm", ServiceFormImpl.class);

        sf.input();

        sf.test();
        sf.end();

        assertTrue(sf.getResult().equals(sf.getResultMax()-td.getUserInput().getCountWrong()));

    }
}
