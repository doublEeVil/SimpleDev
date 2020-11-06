package com.simpledev.redis.bitmap;

import redis.clients.jedis.Jedis;

/**
 * 位图
 */
public class BitMapApp {
    public static void main(String ... args) {
        String key = "TEST:BITMAP";
        Jedis jedis = new Jedis("192.168.3.110", 6379);
        jedis.auth("abc");
        jedis.setbit(key, 6, true);
        jedis.setbit(key, 7, true);
        System.out.println(jedis.bitcount(key));
    }
}
