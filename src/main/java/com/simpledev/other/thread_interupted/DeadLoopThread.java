package com.simpledev.other.thread_interupted;

public class DeadLoopThread implements Runnable {

    public DeadLoopThread() {

    }

    public void start() {
        new Thread(this, "AAAA").start();
    }

    @Override
    public void run() {
        while (true && !Thread.currentThread().isInterrupted()) {
            int sum = 0;
            for (int i = 0; i < 100; i++) {
                sum += i;
            }
            System.out.println("---run");
        }
    }
}
