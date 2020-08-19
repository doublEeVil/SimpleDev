package com.simpledev.rpc.inner_demo.server.impl;

import com.simpledev.rpc.inner_demo.protocol.HelloBService;

import java.util.concurrent.ThreadLocalRandom;

public class HelloBServiceImpl implements HelloBService {
    @Override
    public String randomName(String prefix) {
        return prefix + ThreadLocalRandom.current().nextInt(1234567);
    }
}
