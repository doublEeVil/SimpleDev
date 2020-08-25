package com.simpledev.springboot_comm;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootCommApp {

    public static void main(String ... args) {
        SpringApplication.run(SpringBootCommApp.class);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext context) {
        return args -> {
            for (String s : args) {
                System.out.println("params:" + s);
            }
        };
    }
}
