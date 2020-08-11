package com.simpledev.spring_comm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


////常规注解
@Configuration
@Scope("singleton")
@Lazy
public class BeanA {
    private int id;
    private String tag;

    @Bean(initMethod = "onInit")  // 此处毫无效果
    public BeanA beanA1() {
        return new BeanA();
    }

    @PostConstruct
    public void onInit() {
        System.out.println("----构建对象后执行此init方法");
        this.id = 0;
        this.tag = "tag";
    }

    @PreDestroy
    public void onDestroy() {
        System.out.println("----对象准备销毁：" + this);
    }

    @Override
    public String toString() {
        return "BeanA{" +
                "id=" + id +
                ", tag='" + tag + '\'' +
                '}';
    }
}
