package spring.homework.services;

import org.springframework.stereotype.Service;
import spring.homework.exceptions.ServiceIOException;

import javax.annotation.PreDestroy;
import java.io.*;

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
