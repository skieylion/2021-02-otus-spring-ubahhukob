package spring.homework.services;

import spring.homework.exceptions.ServiceIOException;

public interface ServiceIO {
    void output(String out);
    String input() throws ServiceIOException, ServiceIOException;
}
