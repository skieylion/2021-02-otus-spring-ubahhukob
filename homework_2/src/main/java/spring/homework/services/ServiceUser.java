package spring.homework.services;

import org.springframework.stereotype.Service;
import spring.homework.domain.Survey;
import spring.homework.domain.User;
import spring.homework.exceptions.ServiceIOException;
import spring.homework.exceptions.SurveyException;

public interface ServiceUser {
    User setUserData() throws SurveyException;
    String askUserAnswer() throws SurveyException;
}
