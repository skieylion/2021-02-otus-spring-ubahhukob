package spring.homework.components;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import spring.homework.exceptions.TestException;
import spring.homework.services.ServiceLocale;
import spring.homework.services.TestRunner;


@ShellComponent
public class ComponentCommand {

    private final TestRunner testRunner;
    private final ServiceLocale serviceLocale;

    public ComponentCommand(TestRunner testRunner, ServiceLocale serviceLocale) {
        this.testRunner = testRunner;
        this.serviceLocale = serviceLocale;
    }

    @ShellMethod(value = "start command",key={"start test"})
    public String deleteBook() throws TestException {
        testRunner.run();
        return serviceLocale.localize("cmdMessage");
    }
}
