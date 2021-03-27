package spring.homework.services;

import org.springframework.stereotype.Service;
import spring.homework.domain.ResultTest;
import spring.homework.domain.User;
import spring.homework.exceptions.TestException;

@Service
public class ServiceTestRunner implements TestRunner{

    private final ServiceUser serviceUser;
    private final ServiceTest serviceTest;
    private final ServiceResult serviceResult;

    public ServiceTestRunner(ServiceUser serviceUser, ServiceTest serviceTest, ServiceResult serviceResult) {
        this.serviceUser = serviceUser;
        this.serviceTest = serviceTest;
        this.serviceResult = serviceResult;
    }

    @Override
    public void run() throws TestException {
        User user=serviceUser.inputUserData();
        ResultTest resultTest=serviceTest.test();
        serviceResult.show(resultTest);
    }
}
