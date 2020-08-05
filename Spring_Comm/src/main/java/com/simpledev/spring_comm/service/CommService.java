package com.simpledev.spring_comm.service;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class CommService {

    @PostConstruct
    public void onInit() {
        System.out.println("CommService 已经初始化");
    }
}
