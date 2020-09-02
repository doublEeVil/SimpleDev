package com.simpledev.springboot_cache_ehcache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringBootCacheEhcacheApp {
    public static void main(String ... args) {
        SpringApplication.run(SpringBootCacheEhcacheApp.class);
    }
}
