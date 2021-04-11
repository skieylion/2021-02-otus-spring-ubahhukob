package spring.homework.services;

import org.springframework.stereotype.Service;
import spring.homework.domain.User;
import spring.homework.exceptions.TestException;

@Service
public class ServiceUserImpl implements ServiceUser{

    private final ServiceLocaleConsole serviceIO;

    public ServiceUserImpl(ServiceLocaleConsole serviceIO) {
        this.serviceIO = serviceIO;
    }

    @Override
    public User inputUserData() throws TestException {
        User user=new User();
        serviceIO.output("hello");
        serviceIO.output("yourFirstName");
        user.setFirstName(serviceIO.input());
        serviceIO.output("yourSecondName");
        user.setSecondName(serviceIO.input());
        serviceIO.output("startMessage");
        return user;
    }
}