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

    @Autowired
    private final ServiceUser user;


    private int counterTestResult;

    public ServiceSurveyImpl(SurveyDAO source, ServiceIO serviceIO,MessageSource ms,@Value("${languageTag}") String languageTag,ServiceUser user){
        this.serviceIO=serviceIO;
        this.source=source;
        this.ms=ms;
        this.languageTag=languageTag;
        this.user=user;
        counterTestResult=0;
    }

    private String getValue(String name){
        String value=ms.getMessage(name,null, Locale.forLanguageTag(languageTag));
        return value;
    }



    private void showVariants(Survey f) {
        List<String> variants=f.getVariants();
        for (int i=0;i<variants.size();i++) {
            serviceIO.output("* "+variants.get(i));
        }
    }


    @Override
    public void test() throws SurveyException, ServiceIOException {
        serviceIO.output(getValue("hello"));
        serviceIO.output(getValue("yourFirstName"));
        user.setFirstName(serviceIO.input());
        serviceIO.output(getValue("yourSecondName"));
        user.setSecondName(serviceIO.input());
        serviceIO.output(getValue("startMessage"));

        counterTestResult=0;
        List<Survey> forms=source.findAll();
        for (Survey form:forms) {
            serviceIO.output(form.getQuestion());
            form.getVariants().add(form.getAnswer());
            Collections.shuffle(form.getVariants());
            showVariants(form);
            serviceIO.output(getValue("yourAnswerMessage"));
            String answer=serviceIO.input();
            if(form.getAnswer().equals(answer)==true){
                counterTestResult++;
            }
        }
        String str = String.format(getValue("resultMessage"), counterTestResult, getMaxResultTest());
        serviceIO.output(str);
    }

    public Integer getCurrentResultTest() {
        return counterTestResult;
    }

    public Integer getMaxResultTest() throws SurveyException {
        return source.findAll().size();
    }

}
