package spring.homework.services;

import spring.homework.models.CsvDAO;
import spring.homework.models.DAO;
import spring.homework.models.Form;
import spring.homework.models.ServiceIO;
import spring.homework.services.ServiceConsole;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class ServiceForm {
    private ServiceIO<String,String> serviceIO;
    private DAO source;

    public ServiceForm(String fileName) throws IOException {
        this.serviceIO = new ServiceConsole<>(System.in, System.out);
        this.source=new CsvDAO(fileName);
    }
    private void showVariants(Form f) {
        List<String> variants=f.getVariants();
        for (int i=0;i<variants.size();i++) {
            serviceIO.output("* "+variants.get(i));
        }
    }

    public void show() throws IOException {
        List<Form> forms=source.findAll();
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
