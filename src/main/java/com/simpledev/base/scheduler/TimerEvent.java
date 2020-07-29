package com.simpledev.base.scheduler;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class TimerEvent implements Delayed {
    private Runnable task;
    private TimeUnit timeUnit;
    private long val;

    private boolean onlyOne; // 是否只能存在一个定时事件

    public TimerEvent(Runnable task, TimeUnit timeUnit, long val) {
        this.task = task;
        this.timeUnit = timeUnit;
        this.val = val;
    }

    public TimerEvent(Runnable task, TimeUnit timeUnit, long val, boolean onlyOne) {
        this.task = task;
        this.timeUnit = timeUnit;
        this.val = val;
        this.onlyOne = onlyOne;
    }

    public Runnable getTask() {
        return task;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public long getVal() {
        return val;
    }

    public boolean isOnlyOne() {
        return onlyOne;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(val, timeUnit);
    }

    @Override
    public int compareTo(Delayed o) {
        return this.timeUnit.convert(val,timeUnit) > o.getDelay(timeUnit) ? 1 : -1;
    }
}
