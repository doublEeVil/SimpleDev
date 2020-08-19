package com.simpledev.rpc.inner_demo.comm;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

public class Common {
    static int inc = 0;
    static Map<String, Integer> keyIdMap = new HashMap<>();
    static Map<Integer, String> idKeyMap = new HashMap<>();

    public static int getProtocolId(Class<?> clz, Method method) {
        String key = clz.getName() + "_" + method.getName() + "_";
        for (Parameter parameter : method.getParameters()) {
            key += ("_" + parameter.getType());
        }
        Integer v = keyIdMap.get(key);
        if (v == null) {
            v = inc++;
            keyIdMap.putIfAbsent(key, v);
            idKeyMap.putIfAbsent(v, key);
        }
        return v;
    }
}
