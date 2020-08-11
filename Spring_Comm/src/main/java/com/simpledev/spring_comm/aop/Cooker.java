package com.simpledev.spring_comm.aop;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Cooker {

    // 这一步没法触发aop，因为很可能aop组件并未完成初始化
    @PostConstruct
    public void onInit() {
        cook("手撕包菜");
        try {
            cook("酸菜鱼");
        } catch (Exception e) {

        }
    }

    public String cook(String name) {
        if ("酸菜鱼".equals(name)) {
            throw new RuntimeException("!!!!我不能做酸菜鱼");
        }
        System.out.println("----do cook:" + name);
        return name;
    }
}
