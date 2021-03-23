package spring.homework.services;

import spring.homework.exceptions.ServiceIOException;
import spring.homework.exceptions.SurveyException;

import java.io.IOException;

public interface ServiceIO {
    void output(String out);
    String input() throws SurveyException;
}
