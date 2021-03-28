package spring.homework;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import spring.homework.domain.ItemSurvey;
import spring.homework.domain.ResultTest;
import spring.homework.domain.Survey;
import spring.homework.domain.User;
import spring.homework.exceptions.TestException;
import spring.homework.services.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestPropertySource("classpath:application.properties")
class Homework5ApplicationTests {


	@MockBean
	ServiceUser serviceUser;
	@MockBean
	ServiceTest serviceTest;
	@MockBean
	ServiceResult serviceResult;
	@MockBean
	ServiceIO serviceIO;
	@MockBean
	ServiceLocale serviceLocale;
	@Autowired
	TestRunner testRunner;

	@DisplayName("Тестирование")
	@Test
	void readBook() throws TestException {
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

		int score=rightAnswer.equals(userAnswer)?1:0;
		ResultTest resultTest=new ResultTest(itemSurveyList,score,1);

		Mockito.when(serviceUser.inputUserData()).thenReturn(user);
		Mockito.when(serviceTest.test()).thenReturn(resultTest);
		Mockito.when(serviceLocale.localize("resultMessage")).thenReturn("your result is 1 of the 1 points");
		Mockito.doAnswer((Answer<Void>) invocationOnMock -> {
			Object[] args = invocationOnMock.getArguments();
			ResultTest rt=(ResultTest)args[0];
			serviceIO.output(serviceLocale.localize("resultMessage"));
			return null;
		}).when(serviceResult).show(resultTest);

		testRunner.run();
		Mockito.verify(serviceIO).output("your result is 1 of the 1 points");
	}

}
