package spring.homework.models;

public interface ServiceIO<IN,OUT> {
    public void output(OUT out);
    public IN input();
}
