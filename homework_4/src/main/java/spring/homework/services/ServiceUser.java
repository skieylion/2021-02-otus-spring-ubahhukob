package spring.homework.services;

import spring.homework.domain.User;
import spring.homework.exceptions.TestException;

public interface ServiceUser {
    User inputUserData() throws TestException;
}
