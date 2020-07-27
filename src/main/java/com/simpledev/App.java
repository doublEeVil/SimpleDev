package com.simpledev;

import com.simpledev.base.event.EventBus;
import com.simpledev.dev.test.event.PlayerEvent1;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String ... args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        EventBus.init(context);
        EventBus.post(new PlayerEvent1());
    }
}
