package spring.homework.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import spring.homework.domain.Survey;
import spring.homework.exceptions.TestException;

@Repository
public class CsvSurveyDataDAO extends SurveyDefault<Survey> implements SurveyDAO {

    public CsvSurveyDataDAO(@Value("${csvData}") String fileName){
        super(fileName);
    }

    @Override
    public List<Survey> findAll() throws TestException {
        return super.findAll(Survey.class);
    }
}
