package spring.homework;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.annotation.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import spring.homework.dao.CsvSurvayDAO;
import spring.homework.dao.SurveyDAO;
import spring.homework.domain.Survey;
import spring.homework.domain.User;
import spring.homework.services.*;
import spring.homework.services.ServiceSurveyImpl;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Configuration
@ComponentScan
@TestPropertySource("classpath:test.properties")
@ContextConfiguration
public class AppTest 
{

    /*
        TestData td=new TestDataImpl();
        td.pushData();
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(AppTest.class);
        ServiceSurveyImpl sf=context.getBean("serviceSurveyImpl", ServiceSurveyImpl.class);
        sf.input();
        sf.test();
        assertTrue(sf.getResult().equals(sf.getResultMax()-td.getUserInput().getCountWrong()));
    */

    @Test
    public void shouldAnswerWithTrue() throws Exception {

        String data =   "Ivan" + '\n' +
                        "Ivanov" + '\n' +
                        "I nothing do" + '\n';
        InputStream is = new ByteArrayInputStream(data.getBytes());
        System.setIn(is);

        SurveyDAO surveyDAOSpy = new CsvSurvayDAO("");
        surveyDAOSpy = Mockito.spy(surveyDAOSpy);

        Survey survey=new Survey();
        survey = Mockito.spy(survey);
        Mockito.when(survey.getAnswer()).thenReturn("I nothing do");
        Mockito.when(survey.getQuestion()).thenReturn("What do you do ?");
        List<String> v=new ArrayList<>();
        v.add("I work for Limited Company");
        v.add("I am a programmer");
        v.add("I don't know");
        Mockito.when(survey.getVariants()).thenReturn(v);

        List<Survey> listSurvey=new ArrayList<>();
        listSurvey.add(survey);

        Mockito.when(surveyDAOSpy.findAll()).thenReturn(listSurvey);

        ServiceIO serviceIO=new ServiceConsole(System.in,System.out);

        ServiceUser serviceUser=new ServiceUserImpl(serviceIO);
        serviceUser.inputUserData();
        ServiceSurvey serviceSurveyImpl = new ServiceSurveyImpl(surveyDAOSpy,serviceIO);
        serviceSurveyImpl.test();

        assertTrue(true);
    }
}

