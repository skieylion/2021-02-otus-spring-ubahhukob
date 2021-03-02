package spring.housework.models;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.InputStreamReader;
import java.util.Iterator;

public class CsvToFormBean {
    private Iterator<Form> formsIterator;
    private CsvToFormBean(String fileName) {
        CSVReader reader = new CSVReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(fileName)));
        CsvToBean<Form> csvToBean=  new CsvToBeanBuilder(reader).withType(Form.class).withIgnoreLeadingWhiteSpace(true).build();

        formsIterator = csvToBean.iterator();
    }
    public Iterator<Form> getForms(){
        return formsIterator;
    }
}
