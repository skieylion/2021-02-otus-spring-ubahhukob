package spring.homework;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.context.annotation.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import spring.homework.dao.CsvSurveyDAO;
import spring.homework.dao.SurveyDAO;
import spring.homework.domain.ResultSurvey;
import spring.homework.domain.Survey;
import spring.homework.domain.User;
import spring.homework.exceptions.SurveyException;
import spring.homework.services.*;
import spring.homework.services.ServiceSurveyImpl;


import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
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
    public void shouldAnswerWithTrue2() throws SurveyException {
        SurveyDAO surveyDAO = Mockito.mock(SurveyDAO.class);

        String rightAnswer = "I nothing do";

        Survey survey = new Survey();
        survey.setAnswer(rightAnswer);
        survey.setQuestion("What do you do ?");

        List<String> variants = new ArrayList<>();
        variants.add("I work for Limited Company");
        variants.add("I am a programmer");
        variants.add("I don't know");
        survey.setVariants(variants);

        List<Survey> listSurvey = Collections.singletonList(survey);

        Mockito.when(surveyDAO.findAll()).thenReturn(listSurvey);

        ServiceIO serviceIO = Mockito.mock(ServiceIO.class);
        Mockito.when(serviceIO.input()).thenReturn(rightAnswer);

        ServiceUser serviceUser = Mockito.mock(ServiceUser.class);
        Mockito.when(serviceUser.askUserAnswer()).thenReturn(rightAnswer);

        ServiceResult serviceResult = Mockito.mock(ServiceResult.class);
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                serviceIO.output("your result is 1 of the 1 points");
                return null;
            }
        }).when(serviceResult).show();

        ServiceSurvey serviceSurvey = new ServiceSurveyImpl(surveyDAO, serviceIO,serviceUser,serviceResult);
        serviceSurvey.test();
        Mockito.verify(serviceIO).output("your result is 1 of the 1 points");
    }
}

