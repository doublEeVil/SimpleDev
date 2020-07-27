package com.simpledev;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String ... args) {
        System.out.println("----start app ... ");
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
    }
}
