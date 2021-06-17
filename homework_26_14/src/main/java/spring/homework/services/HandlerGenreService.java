package spring.homework.services;

import org.springframework.stereotype.Service;
import spring.homework.domain.Book;
import spring.homework.domain.Genre;
import spring.homework.h2.domain.AuthorH2;
import spring.homework.h2.domain.BookH2;
import spring.homework.h2.domain.GenreH2;
import spring.homework.h2.repositories.BookDao;
import spring.homework.h2.repositories.GenreDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class HandlerGenreService {
    public GenreH2 handle(Genre mongoGenre){
        GenreH2 genreH2=new GenreH2(mongoGenre.getName());
        return genreH2;
    }
}
