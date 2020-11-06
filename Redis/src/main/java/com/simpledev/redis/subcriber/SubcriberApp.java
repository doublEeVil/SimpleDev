package com.simpledev.redis.subcriber;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

/**
 * redis队列的另外一种使用方式
 * 发布者，订阅者
 */
public class SubcriberApp {

    private static final String channel = "TEST:CHANNEL";

    public void start() {
        JedisPool publishJedis = new JedisPool("192.168.3.110", 6379);
        JedisPool subcriberJdeis = new JedisPool("192.168.3.110", 6379);

        startPublish(publishJedis);
        startSubscriber(subcriberJdeis, 5); // 5个接收者
    }

    private void startPublish(JedisPool pool) {
        new Thread(() -> {
            Jedis jedis = pool.getResource();
            jedis.auth("abc");
            int cnt = 0;
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String msg = cnt++ +" info";
                long n = jedis.publish(channel, msg); // 订阅者数量
                System.out.println("发布消息：" + msg + " 接收者数量：" + n);
            }
        }).start();
    }

    private void startSubscriber(JedisPool pool, int cnt) {
        Runnable run = () -> {
            Jedis jedis = pool.getResource();
            jedis.auth("abc");
            JedisPubSub jedisPubSub = new MyJedisPubSub();
            jedis.subscribe(jedisPubSub, channel);
        };

        for (int i = 0; i < cnt; i++) {
            new Thread(run, "subscriber_" + i).start();
        }
    }

    public static void main(String ... args) {
        new SubcriberApp().start();
    }
}

class MyJedisPubSub extends JedisPubSub {
    @Override
    public void onMessage(String channel, String message) {
        System.out.println("---接收到消息：channel:" + channel + " msg" + message);
    }
}
