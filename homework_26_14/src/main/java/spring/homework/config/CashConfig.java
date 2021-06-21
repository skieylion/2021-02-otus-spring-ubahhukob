package spring.homework.config;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.homework.h2.domain.GenreH2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class CashConfig {

    @Bean
    public CacheManager cacheManager(){
        SimpleCacheManager cacheManager=new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(new ConcurrentMapCache("genre"),new ConcurrentMapCache("author")));
        cacheManager.initializeCaches();
        return cacheManager;
    }
}
