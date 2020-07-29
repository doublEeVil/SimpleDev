package com.simpledev;

import com.simpledev.base.event.EventBus;
import com.simpledev.base.scheduler.SimpleScheduler;
import com.simpledev.base.scheduler.TimerEvent;
import com.simpledev.dev.test.event.PlayerEvent1;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.TimeUnit;

public class App {
    public static void main(String ... args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        EventBus.init(context);
        EventBus.post(new PlayerEvent1());
        EventBus.post(new PlayerEvent1());

        SimpleScheduler.addTimerEvent(new TimerEvent(() -> {
            System.out.println("----5秒后执行");
        }, TimeUnit.SECONDS, 5));

        SimpleScheduler.addTimerEvent(new TimerEvent(() -> {
            System.out.println("----2秒后执行");
        }, TimeUnit.SECONDS, 2));
    }
}
