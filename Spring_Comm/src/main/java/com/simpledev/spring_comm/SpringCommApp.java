package com.simpledev.spring_comm;

import com.simpledev.spring_comm.config.SpringConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringCommApp {
    public static void main(String ... args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        System.out.println("---spring comm start");
    }
}
