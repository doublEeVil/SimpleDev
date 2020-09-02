package com.simpledev.springboot_session;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableRedisHttpSession
public class SpringBootSessionApp {
    public static void main(String ... args) {
        SpringApplication.run(SpringBootSessionApp.class);
    }
}
