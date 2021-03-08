package spring.homework.services;

import java.io.IOException;

public interface ServiceIO<IN,OUT> {
    public void output(OUT out);
    public IN input() throws IOException;
    public void close() throws IOException;
}
