package spring.homework;

import com.opencsv.exceptions.CsvException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.homework.services.ServiceConsole;
import spring.homework.services.ServiceForm;

import java.io.*;

public class App 
{

    public static void main( String[] args ) throws Exception {
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext(
                "appContext.xml"
        );

        ServiceForm sf=context.getBean("serviceForm",ServiceForm.class);
        sf.show();

        context.close();

    }


}
