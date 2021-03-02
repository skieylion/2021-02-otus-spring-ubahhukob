package spring.housework;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        try {
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                    "appContext.xml"
            );
            context.close();
            assertTrue( true );
        } catch (Exception e) {
            assertTrue( false );
        }

    }
}
