package com.simpledev.other.thread_interupted.thread;

import java.util.concurrent.LinkedBlockingQueue;

public class EventLoop {
    Thread thread;
    private LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
    int nameOrder;

    private long recentDead;

    public EventLoop(int nameOrder) {
        this.nameOrder = nameOrder;
        onStart();
    }

    public void onStart() {
        thread = new Thread(() -> {
            while (true) {
                Runnable run = null;
                try {
                    run = queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runCurrent(run);
            }
        }, "CommEventLoop-" + nameOrder);
        thread.start();
    }

    public void submit(Runnable run) {
        queue.add(run);
    }

    private void runCurrent(Runnable runnable) {

        try {
            runnable.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void interruptedSelf() {
        // 禁止1分钟内多次替换线程
        if (System.currentTimeMillis() - recentDead < 60000L) {
            return;
        }
        recentDead = System.currentTimeMillis();
        // 强制停止上一个线程
        thread.stop();
        // 重新开启新的线程
        thread = new Thread(() -> {
            while (true) {
                Runnable run = null;
                try {
                    run = queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runCurrent(run);
            }
        }, "CommEventLoop-" + nameOrder);
        thread.start();
    }
}
