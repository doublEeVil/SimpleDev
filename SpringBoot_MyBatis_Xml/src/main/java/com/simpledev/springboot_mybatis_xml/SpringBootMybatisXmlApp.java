package com.simpledev.springboot_mybatis_xml;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.simpledev.springboot_mybatis_xml.mapper")
public class SpringBootMybatisXmlApp {
    public static void main(String ... args) {
        SpringApplication.run(SpringBootMybatisXmlApp.class);
    }
}
