package com.simpledev.springcloud_consumer_feign.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "service-1")
public interface Service1Api {

    @RequestMapping("/hi")
    String sayHi(@RequestParam(name = "name") String name);
}
