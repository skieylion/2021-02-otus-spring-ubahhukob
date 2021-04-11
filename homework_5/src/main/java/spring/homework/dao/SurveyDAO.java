package spring.homework.dao;

import spring.homework.domain.Survey;
import spring.homework.exceptions.TestException;

import java.util.List;

public interface SurveyDAO {
    List<Survey> findAll() throws TestException;
}
