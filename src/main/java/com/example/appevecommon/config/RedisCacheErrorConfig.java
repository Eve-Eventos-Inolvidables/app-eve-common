package com.example.appevecommon.config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@EnableCaching
public class RedisCacheErrorConfig implements CachingConfigurer {

    private static String cacheName(Cache cache) {
        return cache != null ? cache.getName() : "unknown";
    }

    @Override
    public CacheErrorHandler errorHandler() {
        return new CacheErrorHandler() {

            @Override
            public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
                log.warn("[Redis] Fallo GET en cache='{}', key='{}': {}. Se continua sin cache.",
                        cacheName(cache), key, exception.toString());
                log.debug("[Redis] Detalle del fallo GET en cache='{}'", cacheName(cache), exception);
            }

            @Override
            public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
                log.warn("[Redis] Fallo PUT en cache='{}', key='{}': {}. No se almacena en cache.",
                        cacheName(cache), key, exception.toString());
                log.debug("[Redis] Detalle del fallo PUT en cache='{}'", cacheName(cache), exception);
            }

            @Override
            public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
                log.warn("[Redis] Fallo EVICT en cache='{}', key='{}': {}.",
                        cacheName(cache), key, exception.toString());
                log.debug("[Redis] Detalle del fallo EVICT en cache='{}'", cacheName(cache), exception);
            }

            @Override
            public void handleCacheClearError(RuntimeException exception, Cache cache) {
                log.warn("[Redis] Fallo CLEAR en cache='{}': {}.",
                        cacheName(cache), exception.toString());
                log.debug("[Redis] Detalle del fallo CLEAR en cache='{}'", cacheName(cache), exception);
            }
        };
    }
}
