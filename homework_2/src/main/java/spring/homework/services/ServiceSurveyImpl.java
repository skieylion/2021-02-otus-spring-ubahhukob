package spring.homework.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import spring.homework.dao.CsvDAO;
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

    private String firstName;
    private String secondName;

    private int getCounter() {
        return counter;
    }

    private int counter;

    public ServiceSurveyImpl(SurveyDAO source, ServiceIO serviceIO){
        this.serviceIO=serviceIO;
        this.source=source;
        counter=0;
    }

    @Override
    public List<Survey> getListForm() throws SurveyException {
        return source.findAll();
    }


    private void showVariants(Survey f) {
        List<String> variants=f.getVariants();
        for (int i=0;i<variants.size();i++) {
            serviceIO.output("* "+variants.get(i));
        }
    }


    @Override
    public void test() throws SurveyException, ServiceIOException {
        input();

        counter=0;
        List<Survey> forms=getListForm();
        for (Survey form:forms) {
            serviceIO.output(form.getQuestion());
            form.getVariants().add(form.getAnswer());
            Collections.shuffle(form.getVariants());
            showVariants(form);
            serviceIO.output("your answer:");
            String answer=serviceIO.input();
            if(form.getAnswer().equals(answer)==true){
                counter++;
            }
        }
        serviceIO.output("your result is "+String.valueOf(counter)+" of the "+getResultMax()+" points");
    }

    void input() throws ServiceIOException {
        serviceIO.output("Hello. Nice to see you!");
        serviceIO.output("What is your first name ?");
        firstName=serviceIO.input();
        serviceIO.output("What is your second name ?");
        secondName=serviceIO.input();
        serviceIO.output("Let's test your english ...");
    }

    public Integer getResult() {
        return getCounter();
    }

    public Integer getResultMax() throws SurveyException {
        return getListForm().size();
    }


    public void show() throws SurveyException {
        List<Survey> forms=getListForm();
        for (Survey form:forms) {
            form.getVariants().add(form.getAnswer());
            Collections.shuffle(form.getVariants());
            serviceIO.output("Question: "+form.getQuestion());
            serviceIO.output("Answers: ");
            showVariants(form);
            serviceIO.output("------------------------------------------------");
        }
    }

}
