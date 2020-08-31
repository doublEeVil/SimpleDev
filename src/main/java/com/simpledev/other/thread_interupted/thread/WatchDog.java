package com.simpledev.other.thread_interupted.thread;

import com.simpledev.other.thread_interupted.Dispatcher;
import com.simpledev.other.thread_interupted.msg.Msg;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class WatchDog implements Runnable {

    private Dispatcher dispatcher;

    private Map<Msg, Long> runTimeMap = new ConcurrentHashMap<>();

    public WatchDog(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
        onStart();
    }

    private void onStart() {
        Thread thread = new Thread(this, "WatchDog");
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(2000L);
                Set<Msg> keySet = runTimeMap.keySet();
                long now = System.currentTimeMillis();
                for (Msg msg : keySet) {
                    if (now - runTimeMap.getOrDefault(msg, now) > 10000L) {
                        System.err.println("====可能发生了异常：" + msg);
                    }

                    // 大于30毫秒, 考虑通过中断机制解除死循环
                    if (now - runTimeMap.getOrDefault(msg, now) > 15000L) {
                        System.err.println("====可能发生了异常：" + msg);
                        runTimeMap.remove(msg);
                        dispatcher.eventLoop(msg.getHeader()).interruptedSelf();
                    }
                }
            } catch (Exception e) {

            }
        }
    }

    public void enter(Msg msg) {
        runTimeMap.put(msg, System.currentTimeMillis());
    }

    public void exit(Msg msg) {
        runTimeMap.remove(msg);
    }
}
