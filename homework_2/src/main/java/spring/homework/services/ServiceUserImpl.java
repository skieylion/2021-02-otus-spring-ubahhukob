package spring.homework.services;

import org.springframework.stereotype.Service;
import spring.homework.domain.Survey;
import spring.homework.domain.User;
import spring.homework.exceptions.ServiceIOException;
import spring.homework.exceptions.SurveyException;

@Service
public class ServiceUserImpl implements ServiceUser {

    private final ServiceIO serviceIO;


    public ServiceUserImpl(ServiceIO serviceIO) {
        this.serviceIO = serviceIO;
    }

    @Override
    public User setUserData() throws SurveyException {
        User user=new User();
        serviceIO.output("Hello. Nice to see you!");
        serviceIO.output("What is your first name ?");
        String fName=serviceIO.input();
        user.setFirstName(fName);
        serviceIO.output("What is your second name ?");
        String sName=serviceIO.input();
        user.setSecondName(sName);
        serviceIO.output("Let's test your english ...");
        return user;
    }

    @Override
    public String askUserAnswer() throws SurveyException {
        serviceIO.output("your answer:");
        return serviceIO.input();
    }


}
