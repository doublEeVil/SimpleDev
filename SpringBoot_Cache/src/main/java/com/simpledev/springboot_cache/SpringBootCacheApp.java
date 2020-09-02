package com.simpledev.springboot_cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 使用缓存步骤
 * 1. 主类使用 @EnableCaching
 * 2. 数据库查询类使用 @CacheConfig(cacheNames = "users")
 * 3. 查询方法使用 @Cacheable
 */

@EnableCaching
@SpringBootApplication
public class SpringBootCacheApp {
    public static void main(String ... args) {
        SpringApplication.run(SpringBootCacheApp.class);
    }
}
