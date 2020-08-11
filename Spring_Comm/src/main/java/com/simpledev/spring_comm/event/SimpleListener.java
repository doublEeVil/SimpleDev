package com.simpledev.spring_comm.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class SimpleListener {

    /////////////////// spring 内置事件
    @EventListener
    @Async // 代表异步
    public void onSpringStarted(ContextStartedEvent event) {
        System.out.println("event: ContextStartedEvent");
    }

    @EventListener
    @Async
    public void onSpringRefreshed(ContextRefreshedEvent event) {
        // 默认spring启动到关闭，只会触发这一个事件
        System.out.println("event: ContextRefreshedEvent");
    }

    @EventListener
    @Async
    public void onSpringStopped(ContextStoppedEvent event) {
        System.out.println("event: ContextStoppedEvent");
    }

    @EventListener
    @Async
    public void onSpringClosed(ContextClosedEvent event) {
        System.out.println("event: ContextClosedEvent");
    }

    @EventListener
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("event: ApplicationEvent(这个是总父类事件)");
    }

    /////////////////// 自定义事件
    @EventListener
    public void onMyServerStartedEvent(MyServerStartedEvent event) {
        System.out.println("event: MyServerStartedEvent:" + event.getTime());
    }
}
