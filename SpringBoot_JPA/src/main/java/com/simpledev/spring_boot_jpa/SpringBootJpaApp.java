package com.simpledev.spring_boot_jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 事务的隔离级别有4种，在数据库层面实现
 * 事务的传播方式有7种，因为spring只定义了7种
 */

@SpringBootApplication
public class SpringBootJpaApp {
    public static void main(String ... args) {
        SpringApplication.run(SpringBootJpaApp.class);
    }
}
