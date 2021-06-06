package spring.homework.services;

import spring.homework.domain.Book;

import java.util.List;

public interface ServiceStringBook {
    String convert(Book book);
    String convert(List<Book> book);
}
