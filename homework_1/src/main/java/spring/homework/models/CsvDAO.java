package spring.homework.models;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections.IteratorUtils;

public class CsvDAO implements DAO<Form> {

    private String fileName;

    public CsvDAO(String fileName){
        this.fileName=fileName;
    }

    @Override
    public List<Form> findAll() throws IOException {
        InputStream is=this.getClass().getClassLoader().getResourceAsStream(fileName);
        CSVReader reader = new CSVReader(new InputStreamReader(is));
        CsvToBean<Form> csvToBean=  new CsvToBeanBuilder(reader).withType(Form.class).withIgnoreLeadingWhiteSpace(true).build();


        Iterator<Form>  formsIterator = csvToBean.iterator();
        List<Form> myList=IteratorUtils.toList(formsIterator);
        reader.close();

        return myList;
    }
}
