package spring.homework.services;

import spring.homework.exceptions.BookException;

public interface ServiceBook {
    String read(String id) throws BookException;
    String readAll();
    String update(String bookId, String newName) throws BookException;
    String delete(String bookId);
    String create(String bookName,String authorName,String genreName,String commentValue) throws BookException;
}
