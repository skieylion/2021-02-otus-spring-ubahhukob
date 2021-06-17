package spring.homework.services;

import org.springframework.stereotype.Service;
import spring.homework.domain.Author;
import spring.homework.domain.Book;
import spring.homework.domain.Genre;
import spring.homework.h2.domain.AuthorH2;
import spring.homework.h2.domain.BookH2;
import spring.homework.h2.domain.GenreH2;
import spring.homework.repositories.AuthorDao;
import spring.homework.repositories.BookDao;
import spring.homework.repositories.GenreDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class HandlerAuthorService {

    public AuthorH2 handle(Author mongoAuthor){
        AuthorH2 authorH2=new AuthorH2(mongoAuthor.getFullName(),mongoAuthor.getAlias());
        return authorH2;
    }
}
