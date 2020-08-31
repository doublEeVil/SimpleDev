package com.simpledev.springboot_redis;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRedis {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testString() {
        redisTemplate.opsForValue().set("name", "zjs");
        Assert.assertEquals("zjs", redisTemplate.opsForValue().get("name"));
    }

    @Test
    public void testList() {
        redisTemplate.opsForList().leftPushAll("list", 1, 2, 3, 4, 5);
        List<Integer> list = Arrays.asList(1,2,3,4,5);
        redisTemplate.opsForList().range("list", 0, -1).forEach(v->{
            Assert.assertTrue(list.contains(v));
        });
    }

    @Test
    public void testSet() {

    }

    @Test
    public void testZSet() {
        redisTemplate.opsForZSet().add("stu-score", "AAA", 100);
        redisTemplate.opsForZSet().add("stu-score", "BBB", 10);
        redisTemplate.opsForZSet().add("stu-score", "AAA", 20);
        redisTemplate.opsForZSet().range("stu-score", 0, -1).forEach(k-> {
            System.out.println(k);
        });
    }

    @Test
    public void testHash() {
        Map map = new HashMap();
        map.put("name", "zjs");
        map.put("age", 27);
        redisTemplate.opsForHash().putAll("stu-info", map);
        Assert.assertEquals("zjs", redisTemplate.opsForHash().get("stu-info", "name"));
    }
}
