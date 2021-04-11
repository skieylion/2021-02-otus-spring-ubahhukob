package spring.homework.services;

import org.springframework.stereotype.Service;
import spring.homework.dao.SurveyDAO;
import spring.homework.domain.*;
import spring.homework.exceptions.TestException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ServiceTestImpl implements ServiceTest {

    private final ServiceLocaleConsole serviceIO;
    private final SurveyDAO source;

    public ServiceTestImpl(SurveyDAO source, ServiceLocaleConsole serviceIO){
        this.serviceIO=serviceIO;
        this.source=source;
    }

    private void showVariants(Survey f) {
        List<String> variants=f.getVariants();
        for (int i=0;i<variants.size();i++) {
            serviceIO.output("* "+variants.get(i));
        }
    }
    private String askUserAnswer() throws TestException {
        serviceIO.output("yourAnswerMessage");
        return serviceIO.input();
    }
    private void showSurvey(Survey survey){
        serviceIO.output(survey.getQuestion());
        survey.getVariants().add(survey.getAnswer());
        Collections.shuffle(survey.getVariants());
        showVariants(survey);
    }

    @Override
    public ResultTest test() throws TestException {
        List<Survey> surveys = source.findAll();

        List<ItemSurvey> itemSurveyList=new ArrayList<>();
        int currentScore=0;
        final int maxScore=surveys.size();

        for (Survey survey : surveys) {
            showSurvey(survey);

            String answer=askUserAnswer();

            if(survey.getAnswer().equals(answer)) currentScore++;

            ItemSurvey itemSurvey=new ItemSurvey();
            itemSurvey.setAnswer(answer);
            itemSurvey.setSurvey(survey);
            itemSurveyList.add(itemSurvey);
        }
        ResultTest resultTest=new ResultTest(itemSurveyList,currentScore,maxScore);

        return resultTest;
    }

}
