package spring.homework;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.test.context.ContextConfiguration;
import spring.homework.domain.ItemSurvey;
import spring.homework.domain.ResultTest;
import spring.homework.domain.Survey;
import spring.homework.domain.User;
import spring.homework.exceptions.TestException;
import spring.homework.services.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootTest
@Configuration
@ComponentScan
@ContextConfiguration
public class AppTest
{
	@Test
	public void shouldAnswerWithTrue2() throws TestException {

		User user=new User();
		user.setFirstName("ivan");
		user.setSecondName("ivanov");

		String rightAnswer = "I nothing do";
		String userAnswer = "I nothing do";

		Survey survey = new Survey();
		survey.setAnswer(rightAnswer);
		survey.setQuestion("What do you do ?");

		List<String> variants = new ArrayList<>();
		variants.add("I work for Limited Company");
		variants.add("I am a programmer");
		variants.add("I don't know");
		survey.setVariants(variants);

		ItemSurvey itemSurvey=new ItemSurvey();
		itemSurvey.setAnswer(userAnswer);
		itemSurvey.setSurvey(survey);
		List<ItemSurvey> itemSurveyList = Collections.singletonList(itemSurvey);

		ResultTest resultTest=new ResultTest();
		resultTest.setItemsSurvey(itemSurveyList);
		resultTest.setCurrentScore(rightAnswer.equals(userAnswer)?1:0);
		resultTest.setMaxScore(1);

		ServiceUser serviceUser = Mockito.mock(ServiceUser.class);
		ServiceTest serviceTest = Mockito.mock(ServiceTest.class);
		ServiceResult serviceResult = Mockito.mock(ServiceResult.class);
		ServiceIO serviceIO = Mockito.mock(ServiceIO.class);
		ServiceLocale serviceLocale = Mockito.mock(ServiceLocale.class);

		Mockito.when(serviceUser.inputUserData()).thenReturn(user);
		Mockito.when(serviceTest.test()).thenReturn(resultTest);
		Mockito.when(serviceLocale.localize("resultMessage")).thenReturn("your result is 1 of the 1 points");
		Mockito.doAnswer((Answer<Void>) invocationOnMock -> {
			Object[] args = invocationOnMock.getArguments();
			ResultTest rt=(ResultTest)args[0];
			serviceIO.output(serviceLocale.localize("resultMessage"));
			return null;
		}).when(serviceResult).show(resultTest);

		TestRunner testRunner = new ServiceTestRunner(serviceUser,serviceTest,serviceResult);
		testRunner.run();
		Mockito.verify(serviceIO).output("your result is 1 of the 1 points");
	}
}
