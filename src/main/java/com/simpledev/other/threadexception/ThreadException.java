package com.simpledev.other.threadexception;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadException {
    public static void main(String ... args) {
        // 可以单个线程设置，也可以通用设置
        Thread.setDefaultUncaughtExceptionHandler(((t, e) -> {
            System.out.println("---thread:" + t + " exception:" + e.getMessage());
        }));

        // 线程异常的处理
        // 1. 使用线程池
        ExecutorService pool = Executors.newFixedThreadPool(5);

        Runnable run = () -> {
            System.out.println("----done---");
            throw new NullPointerException();
        };
        int i = 9;
        while (i-- > 0) {
            pool.execute(run);
        }


    }
}
