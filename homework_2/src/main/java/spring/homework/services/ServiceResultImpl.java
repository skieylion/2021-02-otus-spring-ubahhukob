package spring.homework.services;

import org.springframework.stereotype.Service;
import spring.homework.domain.ResultSurvey;
import spring.homework.domain.Survey;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ServiceResultImpl implements ServiceResult {

    private final ServiceIO serviceIO;
    private List<ResultSurvey> resultSurveyList;

    public ServiceResultImpl(ServiceIO serviceIO) {
        this.serviceIO = serviceIO;
        this.resultSurveyList=new ArrayList<>();
    }


    @Override
    public void show() {
        Integer counterRightAnswer=0;
        Integer counterMax=resultSurveyList.size();
        for (ResultSurvey resultSurvey : resultSurveyList) {
            String userAnswer=resultSurvey.getAnswer();
            String rightAnswer=resultSurvey.getSurvey().getAnswer();
            if(rightAnswer.equals(userAnswer)){
                counterRightAnswer++;
            }
        }
        serviceIO.output("your result is " + String.valueOf(counterRightAnswer) + " of the " + counterMax + " points");
    }

    @Override
    public void clear() {
        resultSurveyList.clear();
    }

    @Override
    public void add(ResultSurvey item) {
        resultSurveyList.add(item);
    }
}
