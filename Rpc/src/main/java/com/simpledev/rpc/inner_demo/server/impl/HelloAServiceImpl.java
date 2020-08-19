package com.simpledev.rpc.inner_demo.server.impl;

import com.simpledev.rpc.inner_demo.protocol.HelloAService;

import java.util.concurrent.ThreadLocalRandom;

public class HelloAServiceImpl implements HelloAService {
    @Override
    public int sum(int a, int b) {
        return a + b;
    }

    @Override
    public int sum(int a, int b, int c) {
        return a + b + c;
    }

    @Override
    public int random(int start, int end) {
        return ThreadLocalRandom.current().nextInt(start, end);
    }

    @Override
    public double div(double a, double b) {
        return a / b;
    }
}
