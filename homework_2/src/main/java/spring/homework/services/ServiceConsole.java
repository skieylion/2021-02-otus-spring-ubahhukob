package spring.homework.services;

import org.springframework.stereotype.Service;

import java.io.*;


public class ServiceConsole implements ServiceIO<String,String> {
    private PrintStream out;
    private InputStreamReader is;
    private BufferedReader br;


    public ServiceConsole(InputStream in, PrintStream out){
        this.out=out;
        is=new InputStreamReader(in);
        br = new BufferedReader(is);
    }

    public void output(String out){
        this.out.println(out);
    }
    public String input() throws IOException {
        return br.readLine();
    }

    @Override
    public void close() throws IOException {
        is.close();
        br.close();
    }


}
