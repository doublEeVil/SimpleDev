package com.simpledev.springcloud_consumer_feign.controller;

import com.simpledev.springcloud_consumer_feign.feign.Service1Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HiController {

    @Autowired
    private Service1Api service1Api;

    @RequestMapping("hi")
    public String hi(@RequestParam(value = "name", defaultValue = "guest") String name) {
        return service1Api.sayHi(name);
    }
}
