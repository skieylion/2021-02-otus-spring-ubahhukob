package spring.homework.dao;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.commons.collections.IteratorUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import spring.homework.domain.Survey;
import spring.homework.exceptions.SurveyException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

@Repository
public class CsvDAO implements SurveyDAO {

    private final String fileName;

    public CsvDAO(@Value("${csvFile}") String fileName){
        this.fileName=fileName;
    }

    @Override
    public List<Survey> findAll() throws SurveyException {
        InputStream is=this.getClass().getClassLoader().getResourceAsStream(fileName);
        try(CSVReader reader=new CSVReader(new InputStreamReader(is))){
            CsvToBean<Survey> csvToBean=  new CsvToBeanBuilder(reader).withType(Survey.class).withIgnoreLeadingWhiteSpace(true).build();

            Iterator<Survey>  formsIterator = csvToBean.iterator();
            List<Survey> myList=IteratorUtils.toList(formsIterator);
            return myList;
        }
        catch (Exception e){
            throw new SurveyException(e.getMessage());
        }
    }
}
