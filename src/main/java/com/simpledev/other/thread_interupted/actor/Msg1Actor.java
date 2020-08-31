package com.simpledev.other.thread_interupted.actor;

import com.simpledev.other.thread_interupted.msg.Msg1;

public class Msg1Actor extends Actor<Msg1>{

    @Override
    public void handle(Msg1 msg) {
        System.out.println("---rdv Msg1:" + msg.getName());
        while (msg.getName().endsWith("0")) {

        }
    }
}
