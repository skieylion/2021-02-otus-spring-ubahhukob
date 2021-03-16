package spring.homework.services;

import org.springframework.stereotype.Service;

@Service
public class ServiceUser {
    private  String firstName;
    private  String secondName;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

}
