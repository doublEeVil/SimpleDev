package com.simpledev.dev.test.service;

import com.simpledev.base.event.EventListener;
import com.simpledev.base.event.PlayerEvent;
import org.springframework.stereotype.Service;

@Service
public class TestService1 {

    @EventListener(order = 110)
    public void e1(PlayerEvent pe) {
        System.out.println("===player event 1");
    }

    @EventListener
    public void e2(PlayerEvent pe) {
        System.out.println("===player event 2");
    }
}
