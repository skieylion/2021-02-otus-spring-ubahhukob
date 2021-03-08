package spring.homework.domain;

import com.opencsv.bean.CsvBindAndSplitByPosition;
import com.opencsv.bean.CsvBindByPosition;

import java.util.List;

public class Form {
    @CsvBindByPosition(position = 0)
    private String question;
    @CsvBindByPosition(position = 1)
    private String answer;
    @CsvBindAndSplitByPosition(position = 2, elementType = String.class,splitOn = "_")
    private List<String> variants;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<String> getVariants() {
        return variants;
    }

    public void setVariants(List<String> variants) {
        this.variants = variants;
    }
}
