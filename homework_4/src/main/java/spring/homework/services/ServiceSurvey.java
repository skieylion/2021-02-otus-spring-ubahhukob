package spring.homework.services;

import spring.homework.domain.Survey;
import spring.homework.exceptions.ServiceIOException;
import spring.homework.exceptions.SurveyException;

import java.util.List;

public interface ServiceSurvey {
    void test() throws SurveyException, ServiceIOException;
    Integer getCurrentResultTest();
    Integer getMaxResultTest() throws SurveyException;
}
