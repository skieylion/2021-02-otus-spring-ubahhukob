package spring.homework.services;

import spring.homework.domain.ResultTest;
import spring.homework.exceptions.TestException;

public interface ServiceTest {
    ResultTest test() throws TestException;
}
