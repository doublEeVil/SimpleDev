package com.simpledev.springcloud_consumer_ribbon.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OutService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "hiError")
    public String sayHi(String name) {
        return restTemplate.getForObject("http://SERVICE-1/hi?name="+name, String.class);
    }

    @HystrixCommand(fallbackMethod = "hiError")
    public String sayHi1(String name) {
        return restTemplate.getForObject("http://SERVICE-1/exception?name="+name, String.class);
    }

    @HystrixCommand(fallbackMethod = "hiError")
    public String sayHi2(String name) {
        return restTemplate.getForObject("http://SERVICE-1/sleep?name="+name, String.class);
    }

    // 如果断路，调到此处处理
    public String hiError(String name) {
        return "sorry, something wrong happen ...";
    }
}
