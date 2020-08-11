package com.simpledev.spring_comm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
public class BeanC {
    @Resource
    private String defaultName;
    @Autowired
    private int defaultAge;

    @PostConstruct
    public void onInit() {
        System.out.println("----BeanC init:" + this);
    }

    @Override
    public String toString() {
        return "BeanC{" +
                "defaultName='" + defaultName + '\'' +
                ", defaultAge=" + defaultAge +
                '}';
    }
}
