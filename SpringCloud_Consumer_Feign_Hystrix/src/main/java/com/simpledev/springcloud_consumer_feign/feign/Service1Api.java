package com.simpledev.springcloud_consumer_feign.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "service-1", fallback = Service1ApiException.class)
public interface Service1Api {

    @RequestMapping("/hi")
    String sayHi(@RequestParam(name = "name") String name);

    @RequestMapping("/exception")
    String sayHi1(@RequestParam(name = "name") String name);

    @RequestMapping("/sleep")
    String sayHi2(@RequestParam(name = "name") String name);
}
