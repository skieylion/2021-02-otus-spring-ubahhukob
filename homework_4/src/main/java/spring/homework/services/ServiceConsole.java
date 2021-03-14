package spring.homework.services;

import spring.homework.exceptions.ServiceIOException;

import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class ServiceConsole implements ServiceIO {
    private final PrintStream out;
    private final InputStreamReader is;
    private final BufferedReader br;


    public ServiceConsole(InputStream in, PrintStream out){
        this.out=out;

        is=new InputStreamReader(in);
        br = new BufferedReader(is);
    }

    public void output(String out){
        this.out.println(out);
    }
    public String input() throws ServiceIOException {
        try{
            return br.readLine();
        }
        catch (Exception e){
            throw new ServiceIOException(e.getMessage());
        }
    }

    @PreDestroy
    public void close() throws ServiceIOException {
        try{
            is.close();
            br.close();
        }
        catch (Exception e){
            throw new ServiceIOException(e.getMessage());
        }

    }


}
