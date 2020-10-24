package com.simpledev.tools.guava;

import com.google.common.cache.*;

import java.util.concurrent.TimeUnit;

// 一个简单易用
public class _03_Cache {

    public static void main(String ... args) {

        RemovalListener<String, String> listener = new RemovalListener() {
            @Override
            public void onRemoval(RemovalNotification removalNotification) {
                System.out.println("key:" + removalNotification.getKey() + " val:" + removalNotification.getValue() + " 被移除");
                System.out.println("移除原因：" + removalNotification.getCause());
            }
        };

        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(5) // 最大数量
                .expireAfterAccess(5, TimeUnit.SECONDS) // 过期时间
                .removalListener(listener) // 移除监听
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        return key;
                    }
                }); // 提供构建


        // 使用get方法需要处理异常
        try {
            System.out.println(cache.get("1"));
            System.out.println(cache.get("2"));
            System.out.println(cache.get("3"));
            System.out.println(cache.get("4"));
            System.out.println(cache.get("5"));
            System.out.println(cache.get("6"));

            Thread.sleep(5000);
            // 懒处理模式，如果没有新的读取，是不会触发移除的
            System.out.println(cache.get("7"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
