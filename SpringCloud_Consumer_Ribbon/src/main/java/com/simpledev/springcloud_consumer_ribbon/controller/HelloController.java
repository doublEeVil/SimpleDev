package com.simpledev.springcloud_consumer_ribbon.controller;

import com.simpledev.springcloud_consumer_ribbon.service.OutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private OutService outService;

    @RequestMapping("/hi")
    public String hi(@RequestParam("name") String name) {
        return outService.sayHi(name);
    }
}
