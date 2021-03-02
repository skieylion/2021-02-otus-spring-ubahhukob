package spring.housework.models;

import java.util.Collections;
import java.util.Iterator;

public class FormBean {
    private FormBean(CsvToFormBean csvBean){
        view(csvBean.getForms());
    }
    private void view(Iterator<Form> csvFormIterator){
        while (csvFormIterator.hasNext()) {
            Form form = csvFormIterator.next();
            form.getVariants().add(form.getAnswer());
            Collections.shuffle(form.getVariants());
            System.out.println("Question: "+form.getQuestion());
            System.out.println("Answers: ");
            form.showVariants();
            System.out.println("------------------------------------------------");
        }
    }
}
