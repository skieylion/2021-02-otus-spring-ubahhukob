package spring.homework.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import spring.homework.dao.SurveyDAO;
import spring.homework.exceptions.ServiceIOException;
import spring.homework.exceptions.SurveyException;
import spring.homework.domain.Survey;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Service("serviceSurveyImpl")
public class ServiceSurveyImpl implements ServiceSurvey {
    private final ServiceIO serviceIO;
    private final SurveyDAO source;
    private final MessageSource ms;
    private final String languageTag;

    private String firstName;
    private String secondName;

    private int getCounter() {
        return counter;
    }

    private int counter;

    public ServiceSurveyImpl(SurveyDAO source, ServiceIO serviceIO,MessageSource ms,@Value("${languageTag}") String languageTag){
        this.serviceIO=serviceIO;
        this.source=source;
        this.ms=ms;
        this.languageTag=languageTag;
        counter=0;
    }

    private String getValue(String name){
        String value=ms.getMessage(name,null, Locale.forLanguageTag(languageTag));
        return value;
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
            serviceIO.output(getValue("yourAnswerMessage"));
            String answer=serviceIO.input();
            if(form.getAnswer().equals(answer)==true){
                counter++;
            }
        }
        String str = String.format(getValue("resultMessage"), counter, getResultMax());
        serviceIO.output(str);
    }

    void input() throws ServiceIOException {
        serviceIO.output(getValue("hello"));
        serviceIO.output(getValue("yourFirstName"));
        firstName=serviceIO.input();
        serviceIO.output(getValue("yourSecondName"));
        secondName=serviceIO.input();
        serviceIO.output(getValue("startMessage"));
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
