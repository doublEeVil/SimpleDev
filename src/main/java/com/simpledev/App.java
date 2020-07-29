package com.simpledev;

import com.simpledev.base.event.EventBus;
import com.simpledev.base.scheduler.SimpleScheduler;
import com.simpledev.base.scheduler.TimerEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.util.concurrent.TimeUnit;

public class App {

    public void start() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        EventBus.init(context);

        SimpleScheduler.addTimerEvent(new TimerEvent(() -> {
            System.out.println("----5秒后执行");
        }, TimeUnit.SECONDS, 5));

        SimpleScheduler.addTimerEvent(new TimerEvent(() -> {
            System.out.println("----2秒后执行");
        }, TimeUnit.SECONDS, 2));
    }

    public static void main(String ... args) {
        new App().start();
    }
}
