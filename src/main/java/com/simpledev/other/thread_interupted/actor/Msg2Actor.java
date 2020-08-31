package com.simpledev.other.thread_interupted.actor;

import com.simpledev.other.thread_interupted.msg.Msg2;

public class Msg2Actor extends Actor<Msg2> {

    @Override
    public void handle(Msg2 msg) {
        System.out.println("---rcv: Msg2:" + msg.getOp());
    }
}
