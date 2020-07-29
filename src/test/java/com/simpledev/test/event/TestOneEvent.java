package com.simpledev.test.event;

import com.simpledev.base.event.Event;
import com.simpledev.base.event.EventBus;
import com.simpledev.base.event.EventListener;
import com.simpledev.base.event.PlayerEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-test.xml")
public class TestOneEvent {
    @Autowired
    private ApplicationContext ctx;

    @Test
    public void test() {
        EventBus.init(ctx);
        EventBus.post(new TestEvent1());
    }

    class TestEvent1 extends PlayerEvent {
    }

    @Service
    class TestService {

        @EventListener
        public void onEvent(TestEvent1 event1) {
            System.out.println("----test one event pass---");
        }
    }
}
