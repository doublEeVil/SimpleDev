package com.simpledev.springboot_jdbctemplate_druid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootJdbcTemplateDruidApp {

    public static void main(String ... args) {
        SpringApplication.run(SpringBootJdbcTemplateDruidApp.class);
        System.out.println("浏览器查看 http://127.0.0.1:8888/druid/sql.html");
    }
}
