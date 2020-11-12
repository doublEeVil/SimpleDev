package com.simpledev.redis.queue;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 把redis当成一个队列来使用
 * 主要是用到了redis list结构的特殊性
 */
public class QueueApp {
    private JedisPool producerJedis;
    private JedisPool consumerJdeis;
    private static final String key = "TEST:ORDER_INFO";


    public void start() {
        // jedis不是线程安全的，不能用于多线程环境
        // 初始化两个
        producerJedis = new JedisPool("192.168.3.110", 6379);
        consumerJdeis = new JedisPool("192.168.3.110", 6379);

        // 模拟一个生产者， 消费者
        startProducer();
        startConsumer2();
    }

    private void startProducer() {
        AtomicInteger inc = new AtomicInteger(1);
        Runnable run = () -> {
            Jedis jedis = producerJedis.getResource();
            jedis.auth("abc");
            while (true) {
                try {
                    Thread.sleep(1000L);
                    String order = inc.getAndAdd(1) + "__" + UUID.randomUUID().toString();
                    jedis.lpush(key, order);
                    System.out.println("生产订单：" + order);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        // 启动5个线程
        for (int i = 0; i < 5; i++) {
            new Thread(run).start();
        }
    }

    /**
     * 一直循环的方式。很浪费资源
     */
    private void startConsumer1() {
        Runnable run = () -> {
            Jedis jedis = consumerJdeis.getResource();
            jedis.auth("abc");
            while (true) {
                String orderInfo = jedis.rpop(key);
                if (orderInfo != null) {
                    System.out.println("消费订单：" + orderInfo);
                } else {
                    // Thread.sleep(1000L);
                }
            }
        };
        // 启动5个线程
        for (int i = 0; i < 1; i++) {
            new Thread(run).start();
        }
    }

    /**
     * 采用阻塞的方式
     */
    private void startConsumer2() {
        Runnable run = () -> {
            Jedis jedis = consumerJdeis.getResource();
            jedis.auth("abc");
            while (true) {
                // 采用阻塞命令。5秒超时
                List<String> orderInfo = jedis.brpop(5, key);
                // 类型是按照K:Value
                int index = 0;
                while (index < orderInfo.size()) {
                    index++;
                    System.out.println("消费订单：" + orderInfo.get(index));
                    index++;
                }
            }
        };
        // 启动5个线程
        for (int i = 0; i < 5; i++) {
            new Thread(run).start();
        }
    }

    public static void main(String ... args) {
        new QueueApp().start();
    }
}
