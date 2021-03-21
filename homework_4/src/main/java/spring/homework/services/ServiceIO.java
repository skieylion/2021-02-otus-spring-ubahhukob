package spring.homework.services;

import spring.homework.exceptions.TestException;

public interface ServiceIO {
    void output(String out,Object ...args);
    String input() throws TestException;
}
