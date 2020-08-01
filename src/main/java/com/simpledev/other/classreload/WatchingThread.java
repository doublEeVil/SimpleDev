package com.simpledev.other.classreload;

import java.util.HashMap;
import java.util.Map;

public class WatchingThread extends Thread {
    Map<String, Long> fileMd5Map = new HashMap<>();

    @Override
    public void run() {

    }

    public void start() {
        while (true) {
            try {
                Thread.sleep(1000L);

            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("check--");
        }
    }
}
