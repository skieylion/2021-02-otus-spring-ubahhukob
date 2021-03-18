package spring.homework.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import spring.homework.dao.CsvSurveyDAO;
import spring.homework.dao.SurveyDAO;
import spring.homework.domain.*;
import spring.homework.exceptions.ServiceIOException;
import spring.homework.exceptions.SurveyException;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service("serviceSurveyImpl")
public class ServiceSurveyImpl implements ServiceSurvey {
    private final ServiceIO serviceIO;
    private final SurveyDAO source;
    private final ServiceUser serviceUser;
    private final ServiceResult serviceResult;


    public ServiceSurveyImpl(SurveyDAO source, ServiceIO serviceIO,ServiceUser serviceUser,ServiceResult serviceResult){
        this.serviceIO=serviceIO;
        this.source=source;
        this.serviceUser=serviceUser;
        this.serviceResult=serviceResult;
    }

    private void showVariants(Survey f) {
        List<String> variants=f.getVariants();
        for (int i=0;i<variants.size();i++) {
            serviceIO.output("* "+variants.get(i));
        }
    }

    @Override
    public void test() throws SurveyException {
        serviceUser.setUserData();

        serviceResult.clear();

        List<Survey> surveys = source.findAll();
        for (Survey survey : surveys) {
            serviceIO.output(survey.getQuestion());
            survey.getVariants().add(survey.getAnswer());
            Collections.shuffle(survey.getVariants());
            showVariants(survey);
            String answer=serviceUser.askUserAnswer();
            ResultSurvey resultSurvey=new ResultSurvey();
            resultSurvey.setSurvey(survey);
            resultSurvey.setAnswer(answer);
            serviceResult.add(resultSurvey);
        }
        //serviceIO.output("your result is 1 of the 1 points");
        serviceResult.show();
    }

}
