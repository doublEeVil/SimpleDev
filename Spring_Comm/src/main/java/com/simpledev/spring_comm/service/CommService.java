package com.simpledev.spring_comm.service;

import com.simpledev.spring_comm.test.AbstractAction;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class CommService {

    @PostConstruct
    public void onInit() {
        System.out.println("CommService 已经初始化");
    }
}
