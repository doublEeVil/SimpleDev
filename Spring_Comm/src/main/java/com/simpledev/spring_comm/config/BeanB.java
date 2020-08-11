package com.simpledev.spring_comm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanB {

    @Bean
    public String defaultName() {
        return "lili";
    }

    @Bean
    public int defaultAge() {
        return 24;
    }
}
