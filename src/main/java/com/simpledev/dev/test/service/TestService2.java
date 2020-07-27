package com.simpledev.dev.test.service;

import com.simpledev.base.event.Event;
import com.simpledev.base.event.EventBus;
import com.simpledev.base.event.EventListener;
import com.simpledev.base.event.SystemEvent;
import com.simpledev.dev.test.event.PlayerEvent1;
import org.springframework.stereotype.Service;

@Service
public class TestService2 {

    @EventListener
    public void e1(SystemEvent e) {
        System.out.println("---system event");
        EventBus.post(new PlayerEvent1());
    }

    @EventListener(includeSubEvent = true)
    public void e2(Event event) {
        System.out.println("---all event");
    }
}
