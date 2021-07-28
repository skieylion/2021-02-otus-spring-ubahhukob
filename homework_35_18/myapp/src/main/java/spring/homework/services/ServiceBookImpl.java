package spring.homework.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.homework.domain.Book;
import spring.homework.exceptions.BookException;
import spring.homework.repositories.AuthorRepository;
import spring.homework.repositories.BookRepository;
import spring.homework.repositories.CommentRepository;
import spring.homework.repositories.GenreRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceBookImpl implements ServiceBook{
    private final BookRepository bookRepository;

    public ServiceBookImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> readAll() {
        List<Book> bookList=bookRepository.findAll();
        return bookList;
    }
}
