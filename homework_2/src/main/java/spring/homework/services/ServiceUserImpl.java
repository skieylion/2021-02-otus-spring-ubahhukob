package spring.homework.services;

import org.springframework.stereotype.Service;
import spring.homework.domain.User;
import spring.homework.exceptions.ServiceIOException;
import spring.homework.exceptions.SurveyException;

@Service
public class ServiceUserImpl implements ServiceUser {

    private final ServiceIO serviceIO;
    private User user;

    public ServiceUserImpl(ServiceIO serviceIO) {
        this.serviceIO = serviceIO;
        this.user=new User();
    }

    @Override
    public void inputUserData() throws SurveyException {
        serviceIO.output("Hello. Nice to see you!");
        serviceIO.output("What is your first name ?");
        String fName=serviceIO.input();
        user.setFirstName(fName);
        serviceIO.output("What is your second name ?");
        String sName=serviceIO.input();
        user.setSecondName(sName);
        serviceIO.output("Let's test your english ...");
    }

    @Override
    public User getUserData() {
        return user;
    }


}
