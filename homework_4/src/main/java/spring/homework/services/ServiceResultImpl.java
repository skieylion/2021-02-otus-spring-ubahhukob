package spring.homework.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import spring.homework.config.LocaleConsole;
import spring.homework.domain.ItemSurvey;
import spring.homework.domain.ResultTest;
import spring.homework.domain.Survey;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ServiceResultImpl implements ServiceResult {
    private final ServiceIO serviceIO;

    @Autowired
    public ServiceResultImpl(@LocaleConsole ServiceIO serviceIO) {
        this.serviceIO = serviceIO;
    }

    @Override
    public void show(ResultTest rt) {
        serviceIO.output("resultMessage",rt.getCurrentScore(),rt.getMaxScore());
    }
}
