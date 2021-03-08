package spring.homework.services;

import spring.homework.domain.UserInput;

import java.io.IOException;

public interface TestData {
    public void pushData() throws IOException;
    public UserInput getUserInput();
}
