package com.simpledev.springclouddiscover;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@EnableEurekaServer  // 此注解代表启动一个EurekaServer
@SpringBootApplication
public class App {
    public static void main(String ... args) {
        SpringApplication.run(App.class, args);
    }
}
