package spring.homework.services;

import org.springframework.core.io.ClassPathResource;
import spring.homework.dao.TestCsvDAO;
import spring.homework.domain.UserInput;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestDataImpl implements TestData{
    private UserInput userInput;
    public void pushData() throws IOException {
        Properties configProps = new Properties();
        InputStream iStream = new ClassPathResource("test.properties").getInputStream();
        configProps.load(iStream);

        TestCsvDAO testCsvDAO=new TestCsvDAO(configProps.get("csvFileTest").toString());
        userInput=testCsvDAO.getUserInput();

        StringBuilder sb = new StringBuilder();
        sb.append(userInput.getFirstname()).append('\n');
        sb.append(userInput.getSecondName()).append('\n');

        for (String answer:userInput.getAnswers()) {
            sb.append(answer).append('\n');
        }

        String data = sb.toString();
        InputStream is = new ByteArrayInputStream(data.getBytes());
        System.setIn(is);
    }

    @Override
    public UserInput getUserInput() {
        return userInput;
    }
}
