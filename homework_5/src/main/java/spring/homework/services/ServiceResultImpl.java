package spring.homework.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.homework.domain.ResultTest;

@Service
public class ServiceResultImpl implements ServiceResult {
    private final ServiceLocaleConsole serviceIO;

    @Autowired
    public ServiceResultImpl(ServiceLocaleConsole serviceIO) {
        this.serviceIO = serviceIO;
    }



    @Override
    public void show(ResultTest rt) {
        serviceIO.output("resultMessage",rt.getCurrentScore(),rt.getMaxScore());
    }
}
