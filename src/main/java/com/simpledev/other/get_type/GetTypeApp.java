package com.simpledev.other.get_type;

import java.lang.reflect.ParameterizedType;

public class GetTypeApp {

    public static void main(String ... args) {
        new GetTypeApp().result();
    }

    private void result() {
        ActionA actionA = new ActionA();
        ActionB actionB = new ActionB();

        // 如果是接口定义的泛型 getGenericInterfaces
        // 如果是类定义的泛型 getGenericSuperclass
        ParameterizedType type = (ParameterizedType) actionA.getClass().getGenericInterfaces()[0];
        Class<?> clz = (Class<?>) type.getActualTypeArguments()[0];
        System.out.println("actionA: " + clz);

        type = (ParameterizedType) actionB.getClass().getGenericInterfaces()[0];
        clz = (Class<?>) type.getActualTypeArguments()[0];
        System.out.println("actionB: " + clz);

    }
}
