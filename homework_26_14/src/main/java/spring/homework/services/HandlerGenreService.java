package spring.homework.services;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import spring.homework.domain.Genre;
import spring.homework.h2.domain.GenreH2;

import java.util.Locale;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@RequiredArgsConstructor
public class HandlerGenreService {

    private final CacheManager cacheManager;
    private int increment=0;
    private Lock lock=new ReentrantLock();

    public GenreH2 handle(Genre mongoGenre){
        int y;
        try {
            lock.lock();
            y=++increment;
        } finally {
            lock.unlock();
        }

        GenreH2 genreH2 = new GenreH2(y, mongoGenre.getName());
        cacheManager.getCache("genre").put(mongoGenre.getName(),genreH2);
        return genreH2;
    }
}
