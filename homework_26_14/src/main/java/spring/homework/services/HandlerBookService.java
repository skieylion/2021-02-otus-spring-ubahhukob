package spring.homework.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import spring.homework.domain.Book;
import spring.homework.h2.domain.AuthorH2;
import spring.homework.h2.domain.BookH2;
import spring.homework.h2.domain.GenreH2;
import spring.homework.h2.repositories.AuthorRepositoryH2;
import spring.homework.h2.repositories.GenreRepositoryH2;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class HandlerBookService {

    private final GenreRepositoryH2 genreRepositoryH2;
    private final AuthorRepositoryH2 authorRepositoryH2;
    private final CacheManager cacheManager;

    public BookH2 handle(Book mongoBook){
        String genreName= mongoBook.getGenre().getName();
        GenreH2 genreH2= cacheManager.getCache("genre").get(genreName,GenreH2.class);
        String authorFullName= mongoBook.getAuthor().getFullName();
        AuthorH2 authorH2=cacheManager.getCache("author").get(authorFullName,AuthorH2.class);
        BookH2 bookH2=new BookH2(mongoBook.getName(),authorH2,genreH2,new ArrayList<>());
        return bookH2;
    }
}
