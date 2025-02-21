package br.com.alelo.consumer.consumerpat.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

    /**
     * Configure the cache manager, which is responsible for managing the cache.
     * @return a cache manager that uses a concurrent map to store the cache.
     */
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("types");
    }
}