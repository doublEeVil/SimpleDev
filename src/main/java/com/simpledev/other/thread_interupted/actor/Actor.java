package com.simpledev.other.thread_interupted.actor;

import com.simpledev.other.thread_interupted.msg.Msg;
import com.simpledev.other.thread_interupted.msg.Msg1;
import com.simpledev.other.thread_interupted.msg.Msg2;

import java.util.HashMap;
import java.util.Map;

public abstract class Actor<T extends Msg> {
    public abstract void handle(T msg);

    static Map<Class, Actor> actorMap = new HashMap<>();
    static {
        actorMap.put(Msg1.class, new Msg1Actor());
        actorMap.put(Msg2.class, new Msg2Actor());
    }

    public static Actor getActor(Msg msg) {
        return actorMap.get(msg.getClass());
    }
}
