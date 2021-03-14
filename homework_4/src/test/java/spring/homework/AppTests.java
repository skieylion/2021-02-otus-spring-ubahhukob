package spring.homework;

import static org.junit.Assert.assertSame;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import spring.homework.dao.CsvDAO;
import spring.homework.dao.SurveyDAO;
import spring.homework.domain.Survey;
import spring.homework.exceptions.ServiceIOException;
import spring.homework.exceptions.SurveyException;
import spring.homework.services.ServiceConsole;
import spring.homework.services.ServiceIO;
import spring.homework.services.ServiceSurveyImpl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@SpringBootTest
public class AppTests {

	@Test
	public void contextLoads() throws SurveyException, ServiceIOException {
		String data =   "Ivan" + '\n' +
				"Ivanov" + '\n' +
				"I nothing do" + '\n';
		InputStream is = new ByteArrayInputStream(data.getBytes());
		System.setIn(is);

		SurveyDAO surveyDAOSpy = new CsvDAO("");
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

		ReloadableResourceBundleMessageSource rms=new ReloadableResourceBundleMessageSource();
		rms.setBasename("classpath:i18n/messages");
		MessageSource ms=rms;

		Locale loc=Locale.forLanguageTag("en_US");

		ms = Mockito.spy(ms);
		Mockito.when(ms.getMessage("hello",null, loc)).thenReturn("Hello. Nice to see you!");
		Mockito.when(ms.getMessage("yourFirstName",null, loc)).thenReturn("What is your first name ?");
		Mockito.when(ms.getMessage("yourSecondName",null, loc)).thenReturn("What is your second name ?");
		Mockito.when(ms.getMessage("startMessage",null, loc)).thenReturn("Let's test your knowledge ...");
		Mockito.when(ms.getMessage("yourAnswerMessage",null, loc)).thenReturn("your answer:");
		Mockito.when(ms.getMessage("resultMessage",null, loc)).thenReturn("your result is %d of the %d points");


		ServiceSurveyImpl serviceSurveyImpl = new ServiceSurveyImpl(surveyDAOSpy,serviceIO,ms,"en_US");
		serviceSurveyImpl.test();

		assertSame(serviceSurveyImpl.getResult(),serviceSurveyImpl.getResultMax());
	}

}
