package com.simpledev;

import com.simpledev.base.event.EventBus;
import com.simpledev.dev.test.event.PlayerEvent1;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    public void start() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        EventBus.init(context);
    }

    public static void main(String ... args) {
        new App().start();
    }
}
