package spring.homework.dao;

import spring.homework.domain.UserInput;

import java.io.IOException;
import java.util.List;

public interface TestDAO {
    UserInput getUserInput() throws IOException;
}
