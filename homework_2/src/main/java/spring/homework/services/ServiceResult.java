package spring.homework.services;

import spring.homework.domain.ResultSurvey;

public interface ServiceResult {
    void show();
    void clear();
    void add(ResultSurvey resultSurvey);
}
