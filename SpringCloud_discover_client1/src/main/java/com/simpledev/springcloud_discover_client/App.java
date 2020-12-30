package com.simpledev.springcloud_discover_client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableEurekaClient // 代表可以被服务发现
public class App {
    public static void main(String ... args) {
        SpringApplication.run(App.class, args);
    }

    @RequestMapping("/hi")
    public String hi(@RequestParam(value = "name", defaultValue = "user") String name) {
        return "hi," + name;
    }

    // 模拟异常
    @RequestMapping("/exception")
    public String exception(@RequestParam(value = "name", defaultValue = "user") String name) {
        throw new RuntimeException("unknown error");
    }

    // 模拟超时
    @RequestMapping("/sleep")
    public String sleep(@RequestParam(value = "name", defaultValue = "user") String name) {
        try {
            Thread.sleep(20000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return hi(name);
    }
}
