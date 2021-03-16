package spring.homework.services;

import org.springframework.stereotype.Service;
import spring.homework.domain.User;
import spring.homework.exceptions.ServiceIOException;
import spring.homework.exceptions.SurveyException;

public interface ServiceUser {
    void inputUserData() throws SurveyException;
    User getUserData();
}
