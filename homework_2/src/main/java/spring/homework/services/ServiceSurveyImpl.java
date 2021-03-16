package spring.homework.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import spring.homework.dao.CsvSurvayDAO;
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

    private int counterTestResult;

    public ServiceSurveyImpl(SurveyDAO source, ServiceIO serviceIO){
        this.serviceIO=serviceIO;
        this.source=source;
        counterTestResult=0;
    }

    private void showVariants(Survey f) {
        List<String> variants=f.getVariants();
        for (int i=0;i<variants.size();i++) {
            serviceIO.output("* "+variants.get(i));
        }
    }

    @Override
    public void test() throws SurveyException {

        try {
            counterTestResult = 0;
            List<Survey> surveys = source.findAll();
            for (Survey survey : surveys) {
                serviceIO.output(survey.getQuestion());
                survey.getVariants().add(survey.getAnswer());
                Collections.shuffle(survey.getVariants());
                showVariants(survey);
                serviceIO.output("your answer:");
                String answer = serviceIO.input();
                if (survey.getAnswer().equals(answer)) {
                    counterTestResult++;
                }
            }
            serviceIO.output("your result is " + String.valueOf(counterTestResult) + " of the " + surveys.size() + " points");
        }
        catch (Exception e){
            throw new SurveyException(e);
        }
    }

}
