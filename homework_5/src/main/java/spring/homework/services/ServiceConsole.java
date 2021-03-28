package spring.homework.services;

import spring.homework.exceptions.TestException;

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

    public void output(String out,Object ...args) {
        this.out.println(out);
    }
    public String input() throws TestException {
        try{
            return br.readLine();
        }
        catch (Exception e){
            throw new TestException(e);
        }
    }

}
