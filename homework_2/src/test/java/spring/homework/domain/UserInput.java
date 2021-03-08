package spring.homework.domain;

import com.opencsv.bean.CsvBindAndSplitByPosition;
import com.opencsv.bean.CsvBindByPosition;

import java.util.List;

public class UserInput {
    public UserInput() {
    }

    public String getFirstname() {
        return firstname;
    }

    public String getSecondName() {
        return secondName;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public Integer getCountWrong() {
        return countWrong;
    }

    @CsvBindByPosition(position = 0)
    private String firstname;
    @CsvBindByPosition(position = 1)
    private String secondName;
    @CsvBindByPosition(position = 2)
    private Integer countWrong;
    @CsvBindAndSplitByPosition(position = 3, elementType = String.class,splitOn = "_")
    private List<String> answers;
}
