package com.simpledev.springboot_mybatis;

import com.simpledev.springboot_mybatis.entity.User;
import com.simpledev.springboot_mybatis.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MyBatisTest {

    @Autowired
    private UserMapper userMapper;

    @Before
    public void clear() {
        userMapper.deleteAll();
    }

    @Test
    @Rollback
    public void test() {
        userMapper.insert("AAA", 12);
        User user = userMapper.findByName("AAA");
        Assert.assertEquals(12, user.getAge());

        Map<String, Object> map = new HashMap<>();
        map.put("name", "BBB");
        map.put("age", 78);
        userMapper.insertByMap(map);
        Assert.assertEquals(78, userMapper.findByName("BBB").getAge());

        User user1 = new User("CCC", 45);
        userMapper.insertByUser(user1);
        Assert.assertEquals(45, userMapper.findByName("CCC").getAge());

        userMapper.updateAgeByName(44, "CCC");
        Assert.assertEquals(44, userMapper.findByName("CCC").getAge());

        List<User> all = userMapper.getAll();
        Assert.assertEquals(3, all.size());
    }
}
