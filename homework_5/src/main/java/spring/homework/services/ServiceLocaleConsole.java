package spring.homework.services;

import spring.homework.exceptions.TestException;

public interface ServiceLocaleConsole {
    void output(String out,Object ...args);
    String input() throws TestException;
}
