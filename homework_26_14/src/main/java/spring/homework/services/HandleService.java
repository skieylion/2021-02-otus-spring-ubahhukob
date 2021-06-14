package spring.homework.services;

import org.springframework.stereotype.Service;
import spring.homework.domain.Book;
import spring.homework.h2.domain.Genre;

import java.util.ArrayList;
import java.util.List;

@Service
public class HandleService {
    private List<spring.homework.h2.domain.Genre> listGenre=new ArrayList<>();
    private long genreID=0;
    private List<spring.homework.h2.domain.Author> listAuthor=new ArrayList<>();
    private long authorID=0;
    private long bookID=0;

    public spring.homework.h2.domain.Book doHandleService(Book mongoBook){
        //жанр
        String mongoGenreName=mongoBook.getGenre().getName();
        spring.homework.h2.domain.Genre genre;
        try {
            genre = listGenre.stream().filter(v -> v.getName().equals(mongoGenreName)).findFirst().get();
        } catch (Exception e) {
            genre = new spring.homework.h2.domain.Genre(
                    ++genreID,
                    mongoBook.getGenre().getName()
            );
            listGenre.add(genre);
        }

        //автор
        String mongoAuthorName=mongoBook.getAuthor().getFullName();
        spring.homework.h2.domain.Author author;
        try {
            author = listAuthor.stream().filter(v -> v.getFullName().equals(mongoAuthorName)).findFirst().get();
        } catch (Exception e) {
            author = new spring.homework.h2.domain.Author(
                    ++authorID,
                    mongoBook.getAuthor().getFullName(),
                    mongoBook.getAuthor().getAlias()
            );
            listAuthor.add(author);
        }

        //книга
        spring.homework.h2.domain.Book book=new spring.homework.h2.domain.Book(
                ++bookID,
                mongoBook.getName(),
                author,
                genre
        );
        return book;
    }
}
