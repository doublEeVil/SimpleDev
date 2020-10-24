package com.simpledev.tools.guava;

import com.google.common.reflect.Invokable;
import com.google.common.reflect.Reflection;
import com.google.common.reflect.TypeToken;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class _08_Reflection {
    public static void main(String ... args) {
        // 1. 获取真实类型，例如Map<K,V> 种K，V是何种数据类型
        Map<String, Integer> map = new HashMap<>();


        // 2. 一些更简单的用法，获取类信息
        // jdk 用法

        // 3. 动态代理
        //  jdk用法

    }
}
