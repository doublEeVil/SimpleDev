package com.simpledev.other.classreload;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// 容器类
public class Container {
    // class文件地址
    private String classPath;
    // Class
    private Map<String, Class<?>> clzMap = new HashMap<>();
    // 监视文件线程
    private WatchingThread watchingThread;
    // 监听器
    private ClassChangeListener listener;

    public Container(String baseClassPath) {
        classPath = baseClassPath;
        start();
    }

    public Container(String baseClassPath, ClassChangeListener listener) {
        classPath = baseClassPath;
        this.listener = listener;
    }

    private void start() {
        watchingThread = new WatchingThread(classPath, this);
        watchingThread.start();
    }

    public Class<?> getOrLoadClz(String className) {
        return clzMap.computeIfAbsent(className, (k) -> {
            Class<?> clz = new SimpleClassLoader(classPath).findClassOrNull(className);
            if (clz != null && listener != null) {
                listener.onClassChange(clz);
            }
            watchingThread.add(className);
            return clz;
        });
    }

    public void setListener(ClassChangeListener listener) {
        this.listener = listener;
    }
}
