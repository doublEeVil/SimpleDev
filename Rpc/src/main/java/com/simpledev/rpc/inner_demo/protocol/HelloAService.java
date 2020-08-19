package com.simpledev.rpc.inner_demo.protocol;

public interface HelloAService {
    int sum(int a, int b);
    int sum(int a, int b, int c);
    int random(int start, int end);
    double div(double a, double b);
}
