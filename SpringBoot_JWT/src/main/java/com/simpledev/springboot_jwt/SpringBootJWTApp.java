package com.simpledev.springboot_jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan("com.simpledev.springboot_jwt")  //不加这个实在是找不到过滤器
@SpringBootApplication
public class SpringBootJWTApp {
    public static void main(String ... args) {
        SpringApplication.run(SpringBootJWTApp.class);
    }
}
