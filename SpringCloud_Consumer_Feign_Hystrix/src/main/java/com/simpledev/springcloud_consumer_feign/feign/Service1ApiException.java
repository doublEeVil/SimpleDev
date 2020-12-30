package com.simpledev.springcloud_consumer_feign.feign;

import org.springframework.stereotype.Component;

@Component
public class Service1ApiException implements Service1Api{
    @Override
    public String sayHi(String name) {
        return "service unknown error";
    }

    @Override
    public String sayHi1(String name) {
        return "an exception happen";
    }

    @Override
    public String sayHi2(String name) {
        return "long time no return ....";
    }
}
