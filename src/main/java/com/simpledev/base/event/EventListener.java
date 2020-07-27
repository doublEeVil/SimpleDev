package com.simpledev.base.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventListener {
    int order() default 100; // 监听顺序
    boolean includeSubEvent() default false; // 是否监听子事件
}
