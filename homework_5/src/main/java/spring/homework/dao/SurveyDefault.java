package spring.homework.dao;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.commons.collections.IteratorUtils;
import spring.homework.domain.Survey;
import spring.homework.exceptions.TestException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

public class SurveyDefault<T> {

    private final String fileName;

    public SurveyDefault(String fileName) {
        this.fileName = fileName;
    }

    protected List<T> findAll(Class<? extends T> type) throws TestException {
        InputStream is=this.getClass().getClassLoader().getResourceAsStream(fileName);
        try(CSVReader reader=new CSVReader(new InputStreamReader(is))){
            CsvToBean<T> csvToBean=  new CsvToBeanBuilder(reader).withType(type).withIgnoreLeadingWhiteSpace(true).build();

            Iterator<T> formsIterator = csvToBean.iterator();
            List<T> myList= IteratorUtils.toList(formsIterator);
            return myList;
        }
        catch (Exception e){
            throw new TestException(e);
        }
    }
}
