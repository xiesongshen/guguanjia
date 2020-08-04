package com.xss.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisOperations;

import java.util.ArrayList;

/**
 * @author XSS
 * @date 2020/7/28
 * @desc
 */
@Configuration
@EnableCaching
public class SpringCacheConfig {

    @Bean
    public CacheManager cacheManager(RedisOperations redisOperations) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisOperations);
        ArrayList<String> list = new ArrayList<>();
        list.add("officeCache");

        cacheManager.setCacheNames(list);

        cacheManager.setDefaultExpiration(600);

        return cacheManager;
    }
}
