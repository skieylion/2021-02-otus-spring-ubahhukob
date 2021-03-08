package spring.homework.services;

import spring.homework.domain.Form;

import java.io.IOException;
import java.util.List;

public interface ServiceForm {
    List<Form> getListForm() throws IOException;
    public void test() throws IOException;
    public void input() throws IOException;
    public Integer getResult();
    public Integer getResultMax() throws IOException;
    public void end() throws IOException;
}
