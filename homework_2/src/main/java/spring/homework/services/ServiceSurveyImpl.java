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

    @Autowired
    private final ServiceUser user;

    private int counterTestResult;

    public ServiceSurveyImpl(SurveyDAO source, ServiceIO serviceIO,ServiceUser user){
        this.user=user;
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
    public void test() throws SurveyException, ServiceIOException {
        serviceIO.output("Hello. Nice to see you!");
        serviceIO.output("What is your first name ?");
        String fName=serviceIO.input();
        user.setFirstName(fName);
        serviceIO.output("What is your second name ?");
        String sName=serviceIO.input();
        user.setSecondName(sName);
        serviceIO.output("Let's test your english ...");

        counterTestResult=0;
        List<Survey> forms=source.findAll();
        for (Survey form:forms) {
            serviceIO.output(form.getQuestion());
            form.getVariants().add(form.getAnswer());
            Collections.shuffle(form.getVariants());
            showVariants(form);
            serviceIO.output("your answer:");
            String answer=serviceIO.input();
            if(form.getAnswer().equals(answer)==true){
                counterTestResult++;
            }
        }
        serviceIO.output("your result is "+String.valueOf(counterTestResult)+" of the "+getMaxResultTest()+" points");
    }

    public Integer getCurrentResultTest() {
        return counterTestResult;
    }

    public Integer getMaxResultTest() throws SurveyException {
        return source.findAll().size();
    }

}
