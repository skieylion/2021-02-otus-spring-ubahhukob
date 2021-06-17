package spring.homework.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import spring.homework.domain.Book;
import spring.homework.domain.Genre;
import spring.homework.h2.domain.AuthorH2;
import spring.homework.h2.domain.BookH2;
import spring.homework.h2.domain.GenreH2;
import spring.homework.h2.repositories.AuthorDao;
import spring.homework.h2.repositories.GenreDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class HandlerBookService {

    @Autowired
    @Qualifier("genreDaoH2")
    private GenreDao genreDaoH2;
    @Autowired
    @Qualifier("authorDaoH2")
    private AuthorDao authorDaoH2;

    public BookH2 handle(Book mongoBook){
        String genreName= mongoBook.getGenre().getName();
        GenreH2 genreH2=genreDaoH2.findByName(genreName).get(0);
        String authorFullName= mongoBook.getAuthor().getFullName();
        AuthorH2 authorH2=authorDaoH2.findByFullName(authorFullName).get(0);
        BookH2 bookH2=new BookH2(mongoBook.getName(),authorH2,genreH2,new ArrayList<>());
        return bookH2;
    }
}
