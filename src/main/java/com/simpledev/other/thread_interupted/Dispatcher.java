package com.simpledev.other.thread_interupted;

import com.simpledev.other.thread_interupted.actor.Actor;
import com.simpledev.other.thread_interupted.msg.Msg;
import com.simpledev.other.thread_interupted.thread.EventLoop;
import com.simpledev.other.thread_interupted.thread.WatchDog;

public class Dispatcher {
    private static Dispatcher INS = new Dispatcher();

    private EventLoop[] eventLoops;

    private Dispatcher() {
        eventLoops = new EventLoop[2];
        for (int i = 0; i < 2; i++) {
            eventLoops[i] = new EventLoop(i);
        }
    }

    public static Dispatcher getINS() {
        return INS;
    }

    private WatchDog watchDog = new WatchDog(this);

    public void dispatch(Msg msg) {
        watchDog.enter(msg);
        eventLoops[msg.getHeader() & 1].submit(()->{
            Actor.getActor(msg).handle(msg);
            watchDog.exit(msg);
        });
    }

    public EventLoop eventLoop(int index) {
        return eventLoops[index & 1];
    }
}
