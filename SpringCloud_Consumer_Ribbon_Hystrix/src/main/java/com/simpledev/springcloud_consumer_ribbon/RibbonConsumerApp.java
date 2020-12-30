package com.simpledev.springcloud_consumer_ribbon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix //断路器
public class RibbonConsumerApp {
    public static void main(String ... args) {
        SpringApplication.run(RibbonConsumerApp.class, args);
    }

    @Bean
    @LoadBalanced // 负载均衡
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
