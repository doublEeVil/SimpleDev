package com.simpledev.tools.guava;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

// 非常好用事件处理机制
public class _02_EventBus {

    public static void main(String ... args) {
        new _02_EventBus().test();
    }

    private void test() {
        EventBus eventBus = new EventBus();
        // 手动注册，只要有@Subscribe即可， 如果没有该注解，则不会添加
        eventBus.register(this);
        eventBus.post(new SendMsgEvent("abcd"));
    }

    @Subscribe
    private void onSendMsg(SendMsgEvent event) {
        System.out.println("----" + event.msg);
    }

    class SendMsgEvent {
        String msg;

        public SendMsgEvent(String msg) {
            this.msg = msg;
        }
    }
}
