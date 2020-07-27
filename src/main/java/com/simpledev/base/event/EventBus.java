package com.simpledev.base.event;

import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.*;

public class EventBus {

    static Map<Class, Set<EventMethodWrapper>> invokerMap = new HashMap<>();
    static Comparator<EventMethodWrapper> comparable = (o1, o2) -> o1.getOrder() - o2.getOrder();

    static Set<EventMethodWrapper> subInvokerSet = new TreeSet<>(comparable);

    public static void init(ApplicationContext context) {
        String[] all = context.getBeanDefinitionNames();
        Object target;
        for (String s : all) {
            if (s.startsWith("org.springframework")) {
                continue;
            }
            target = context.getBean(s);
            Method[] methods = target.getClass().getMethods();
            for (Method method : methods) {
                method.setAccessible(true);
                if (!method.isAnnotationPresent(EventListener.class)) {
                    continue;
                }
                if (method.getParameters().length != 1) {
                    continue;
                }
                Class<?> key = method.getParameterTypes()[0];
                // 参数类型不要搞反了
                if (!Event.class.isAssignableFrom(key)) {
                    continue;
                }
                EventListener event = method.getAnnotation(EventListener.class);
                EventMethodWrapper wrapper = new EventMethodWrapper(target, method, event.order());
                if (invokerMap.containsKey(key)) {
                    invokerMap.get(key).add(wrapper);
                } else {
                    Set<EventMethodWrapper> set = new TreeSet<>(comparable);
                    set.add(wrapper);
                    invokerMap.put(key, set);
                }
                if (event.includeSubEvent()) {
                    wrapper.setClz(key);
                    subInvokerSet.add(wrapper);
                }
            }
        }
    }

    static ExecutorService pool = new ThreadPoolExecutor(3, 200,
            60L, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>());

    public static void post(Event event) {
        pool.submit(() -> {
            // 如果监听了父事件
            subInvokerSet.forEach(v -> {
                if (v.getClz() == event.getClass()) {
                    // 过滤掉自己，不然会执行两次
                    return;
                }
                if (v.getClz().isAssignableFrom(event.getClass())) {
                    v.invoke(event);
                }
            });
            invokerMap.get(event.getClass()).forEach(k -> {
                k.invoke(event);
            });
        });
    }

    static class EventMethodWrapper {
        private Object target;
        private Method method;
        private int order;
        private Class clz;

        public EventMethodWrapper(Object target, Method method, int order) {
            this.target = target;
            this.method = method;
            this.order = order;
        }

        public Class getClz() {
            return clz;
        }

        public void setClz(Class clz) {
            this.clz = clz;
        }

        public int getOrder() {
            return order;
        }

        public void invoke(Event event) {
            try {
                method.invoke(target, event);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public void onStop() {

    }
}
