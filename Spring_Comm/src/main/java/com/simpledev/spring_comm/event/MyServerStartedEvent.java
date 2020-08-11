package com.simpledev.spring_comm.event;

public class MyServerStartedEvent {
    private long time;

    public MyServerStartedEvent(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }
}
