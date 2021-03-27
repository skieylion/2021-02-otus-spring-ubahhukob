package spring.homework.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import spring.homework.config.Console;
import spring.homework.config.LocaleConsole;
import spring.homework.domain.User;
import spring.homework.exceptions.TestException;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Service
public class ServiceUserImpl implements ServiceUser{

    private final ServiceIO serviceIO;

    public ServiceUserImpl(@LocaleConsole ServiceIO serviceIO) {
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