package spring.homework.services;

import spring.homework.domain.Book;
import spring.homework.exceptions.BookException;

import java.util.List;

public interface ServiceBook {
    List<Book> readAll();
}
