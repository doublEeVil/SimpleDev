package com.simpledev.spring_comm;

import com.simpledev.spring_comm.aop.Cooker;
import com.simpledev.spring_comm.config.SpringConfig;
import com.simpledev.spring_comm.event.MyServerStartedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringCommApp {
    public static void main(String ... args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        System.out.println("---spring comm start");
        System.out.println("-----init bean-----");
        System.out.println(context.getBean("beanA"));

        System.out.println("----发布一个自定义事件----");
        context.publishEvent(new MyServerStartedEvent(System.currentTimeMillis()));

        context.getBean(Cooker.class).cook("包子");
        try {
            context.getBean(Cooker.class).cook("酸菜鱼");
        } catch (Exception e) {

        }
    }
}
