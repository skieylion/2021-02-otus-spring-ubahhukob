package spring.homework.dao;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.commons.collections.IteratorUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import spring.homework.domain.Form;
import spring.homework.domain.UserInput;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

public class TestCsvDAO implements TestDAO {
    private String fileName;

    public TestCsvDAO(String fileName){
        System.out.println(fileName);
        this.fileName=fileName;
    }

    @Override
    public UserInput getUserInput() throws IOException {
        InputStream is=this.getClass().getClassLoader().getResourceAsStream(fileName);
        CSVReader reader = new CSVReader(new InputStreamReader(is));
        CsvToBean<UserInput> csvToBean=  new CsvToBeanBuilder(reader).withType(UserInput.class).withIgnoreLeadingWhiteSpace(true).build();
        List<UserInput> myList= csvToBean.parse();
        reader.close();
        return myList.get(0);
    }
}
