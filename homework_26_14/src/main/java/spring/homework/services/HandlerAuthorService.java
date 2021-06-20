package spring.homework.services;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import spring.homework.domain.Author;
import spring.homework.h2.domain.AuthorH2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@RequiredArgsConstructor
public class HandlerAuthorService {

    private final CacheManager cacheManager;
    private int increment=0;
    private Lock lock=new ReentrantLock();

    public AuthorH2 handle(Author mongoAuthor){
        int y;
        try {
            lock.lock();
            y=++increment;
        } finally {
            lock.unlock();
        }

        AuthorH2 authorH2 = new AuthorH2(y, mongoAuthor.getFullName(), mongoAuthor.getAlias());
        cacheManager.getCache("author").put(authorH2.getFullName(), authorH2);
        return authorH2;

    }
}
