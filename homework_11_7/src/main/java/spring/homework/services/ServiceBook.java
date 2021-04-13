package spring.homework.services;

public interface ServiceBook {
    String read(long id);
    String readAll();
    String update(long bookId, String newName);
    String delete(long bookId);
    String create(String bookName,String authorName,String genreName,String commentValue);
}
