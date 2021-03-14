package spring.homework.dao;

import spring.homework.domain.Survey;
import spring.homework.exceptions.SurveyException;

import java.util.List;

public interface SurveyDAO {
    List<Survey> findAll() throws SurveyException;
}
