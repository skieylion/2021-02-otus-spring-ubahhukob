package spring.homework.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import spring.homework.dao.CsvDAO;
import spring.homework.dao.DAO;
import spring.homework.domain.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service("serviceForm")
public class ServiceFormImpl implements ServiceForm {
    private ServiceIO<String,String> serviceIO;
    private DAO source;
    private String firstName;
    private String secondName;

    private int getCounter() {
        return counter;
    }

    private int counter;


    public ServiceFormImpl(@Autowired DAO source, @Autowired ServiceIO<String,String> serviceIO){
        this.serviceIO=serviceIO;
        this.source=source;
        counter=0;
    }

    @Override
    public List<Form> getListForm() throws IOException {
        return source.findAll();
    }



    private void showVariants(Form f) {
        List<String> variants=f.getVariants();
        for (int i=0;i<variants.size();i++) {
            serviceIO.output("* "+variants.get(i));
        }
    }


    @Override
    public void test() throws IOException {
        counter=0;
        List<Form> forms=getListForm();
        for (Form form:forms) {
            serviceIO.output(form.getQuestion());
            form.getVariants().add(form.getAnswer());
            Collections.shuffle(form.getVariants());
            showVariants(form);
            serviceIO.output("your answer:");
            String answer=serviceIO.input();
            if(form.getAnswer().equals(answer)==true){
                counter++;
            }
        }
        serviceIO.output("your result is "+String.valueOf(counter)+" of the "+getResultMax()+" points");
    }

    @Override
    public void input() throws IOException {
        serviceIO.output("Hello. Nice to see you!");
        serviceIO.output("What is your first name ?");
        firstName=serviceIO.input();
        serviceIO.output("What is your second name ?");
        secondName=serviceIO.input();
        serviceIO.output("Let's test your english ...");
    }

    @Override
    public Integer getResult() {
        return getCounter();
    }

    @Override
    public Integer getResultMax() throws IOException {
        return getListForm().size();
    }

    @Override
    public void end() throws IOException {
        serviceIO.close();
    }


    public void show() throws IOException {
        List<Form> forms=getListForm();
        for (Form form:forms) {
            form.getVariants().add(form.getAnswer());
            Collections.shuffle(form.getVariants());
            serviceIO.output("Question: "+form.getQuestion());
            serviceIO.output("Answers: ");
            showVariants(form);
            serviceIO.output("------------------------------------------------");
        }
    }

}
