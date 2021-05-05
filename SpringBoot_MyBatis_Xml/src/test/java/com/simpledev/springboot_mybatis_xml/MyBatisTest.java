package com.simpledev.springboot_mybatis_xml;

import com.simpledev.springboot_mybatis_xml.entity.User;
import com.simpledev.springboot_mybatis_xml.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
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
        //userMapper.deleteAll();
    }

    @Test
    public void findOne() {
        User user = userMapper.findByName("1");
        System.out.println(user);
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

    @Test
    public void saveMany() {
        int size = 10000;
        int i = 0;
        int maxSize = 100_0000;
        List<User> list = new ArrayList<>(size);
        for (; maxSize >=0; maxSize -= size) {
            for (i=0;i < size; i++) {
                User user = new User();
                user.setName(i+"");
                user.setAge(i);
                list.add(user);
            }
            userMapper.saveMany(list);
            list.clear();
        }
    }
}
