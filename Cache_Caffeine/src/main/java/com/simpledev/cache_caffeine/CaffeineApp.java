package com.simpledev.cache_caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.RemovalListener;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Acffeine是一个缓存框架
 * 支持容量策略
 * 支持时间策略
 * 支持引用策略
 *
 * 也可以多种策略组合使用
 */
public class CaffeineApp {

    public static void main(String ... args) {
        // new CaffeineApp().withMaxsize();
        // new CaffeineApp().withExpireTime();
        // new CaffeineApp().withWeakValue();
        new CaffeineApp().testChangeSystemTime();
    }

    /**
     * 最大容量策略.
     * 这里有个问题：如果设置的最大容量是4，同时插入5个元素，会发现5个元素是同时存在的，并没有过期
     */
    public void withMaxsize() {
        // 构建一个最大容量是是2的cache
        Cache<Integer, User> cache = Caffeine.newBuilder()
                .initialCapacity(2)
                .maximumSize(2)
                .build();
        User u1 = new User(1, "A");
        User u2 = new User(2, "A");
        User u3 = new User(3, "A");
        User u4 = new User(4, "A");
        cache.put(u1.id,u1);
        cache.put(u2.id,u2);
        cache.put(u3.id,u3);
        cache.put(u4.id,u4);
        // 分别访问 u1,u3,u4
        cache.getIfPresent(1);
        cache.getIfPresent(3);
        cache.getIfPresent(4);
        // 再插入一个新的
        cache.put(5, new User(5, "B"));
        // 此时u2不在缓存了
        cache.asMap().forEach( (k,v) -> {
            System.out.println("k:" + k + " v:" + v);
        });
        System.out.println(cache.getIfPresent(2));
    }

    /**
     * 过期策略
     */
    public void withExpireTime() {
        Cache<Integer, User> cache = Caffeine.newBuilder()
                .expireAfterAccess(3, TimeUnit.SECONDS)
                // 需要调用cleanup
                .removalListener(new RemovalListener() {

                    @Override
                    public void onRemoval(@Nullable Object key, @Nullable Object value, @NonNull RemovalCause removalCause) {
                        System.out.println("---元素被移除：" + value);
                    }
                })
                .build();
        cache.put(1, new User(1, "A"));
        cache.put(2, new User(2, "B"));
        try {
            Thread.sleep(4000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cache.put(3, new User(3, "C"));
        // 此时得到是null, 说明生效了
        System.out.println(cache.getIfPresent(1));
        // 如果不调用这个，上面的监听器并不生效
        cache.cleanUp();
    }

    /**
     * 根据引用来管理缓存
     */
    public void withWeakValue() {
        Cache<Integer, User> cache = Caffeine.newBuilder()
                .weakValues()
                .build();
        User u1 = new User(1, "A");
        User u2 = new User(2, "B");
        cache.put(1, u1);
        cache.put(2, u2);

        u2 = null;
        System.gc();
        System.out.println(cache.getIfPresent(1));
        // 被回收了，此时打印null
        System.out.println(cache.getIfPresent(2));
    }

    /**
     * 手动删除缓存
     */
    public void removeByMySelf() {
        Cache<Integer, User> cache = Caffeine.newBuilder()
                .initialCapacity(2)
                .maximumSize(16)
                .expireAfterAccess(3, TimeUnit.SECONDS)
                .build();
        cache.put(1, new User(1, "A"));
        cache.invalidate(1);
        cache.invalidateAll();
    }

    /**
     * 测试下更改系统时间是否会触发缓存移除
     * 答案是不可以：更改系统时间无效。。。。
     */
    public void testChangeSystemTime() {
        Cache<Integer, User> cache = Caffeine.newBuilder()
                .expireAfterAccess(3, TimeUnit.MINUTES)
                // 需要调用cleanup
                .removalListener(new RemovalListener() {

                    @Override
                    public void onRemoval(@Nullable Object key, @Nullable Object value, @NonNull RemovalCause removalCause) {
                        System.out.println("---元素被移除：" + value);
                    }
                })
                .build();
        cache.put(1, new User(1, "A"));
        cache.put(2, new User(2, "B"));

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            if (line.equals("q")) {
                System.out.println("q: " + cache.getIfPresent(1));
            }
        }
    }

    class User {
        int id;
        String name;

        public User(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
