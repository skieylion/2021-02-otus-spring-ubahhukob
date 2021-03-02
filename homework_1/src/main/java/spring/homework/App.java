package spring.housework;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.opencsv.CSVReader;
import spring.housework.models.Form;

import java.io.*;
import java.util.*;

/**
 * Hello world!
 *
 */
public class App 
{

    public static void main( String[] args ) throws IOException, CsvException {
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext(
                "appContext.xml"
        );

        context.close();

    }


}
