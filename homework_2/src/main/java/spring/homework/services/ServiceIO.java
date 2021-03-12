package spring.homework.services;

import spring.homework.exceptions.ServiceIOException;

import java.io.IOException;

public interface ServiceIO {
    void output(String out);
    String input() throws ServiceIOException;
}
