package spring.housework;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.homework.services.ServiceForm;

import java.io.IOException;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() throws Exception {
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext(
                "appContext.xml"
        );

        ServiceForm sf=context.getBean("serviceForm",ServiceForm.class);
        sf.show();

        context.close();

        assertTrue( true );

    }
}
