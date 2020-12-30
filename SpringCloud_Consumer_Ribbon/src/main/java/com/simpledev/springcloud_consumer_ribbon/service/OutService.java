package com.simpledev.springcloud_consumer_ribbon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OutService {

    @Autowired
    private RestTemplate restTemplate;

    public String sayHi(String name) {
        return restTemplate.getForObject("http://SERVICE-1/hi?name="+name, String.class);
    }
}
