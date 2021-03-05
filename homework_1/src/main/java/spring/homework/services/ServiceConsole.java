package spring.homework.services;

import spring.homework.models.ServiceIO;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class ServiceConsole<IN,OUT> implements ServiceIO<IN,OUT> {
    private InputStream in;
    private PrintStream out;

    public ServiceConsole(InputStream in, PrintStream out){
        this.in=in;
        this.out=out;
    }

    public void output(OUT out){
        this.out.println(out);
    }
    public IN input(){
        return null;
    }

}
