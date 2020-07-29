package com.simpledev.base.scheduler;

import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

public class SimpleScheduler {
    private static ScheduledExecutorService pool = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());

    private static DelayQueue queue = new DelayQueue();

    static ThreadPoolExecutor pool2
            = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
            100,
            0,
            NANOSECONDS,
            queue);

    public static void addTimerEvent(TimerEvent timerEvent) {
        pool.schedule(timerEvent.getTask(), timerEvent.getVal(), timerEvent.getTimeUnit());
        //queue.add(timerEvent);
    }
}
