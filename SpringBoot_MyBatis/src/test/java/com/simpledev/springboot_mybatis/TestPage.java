package com.simpledev.springboot_mybatis;

import com.simpledev.springboot_mybatis.entity.User;
import com.simpledev.springboot_mybatis.mapper.UserMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestPage {
    @Autowired
    private UserMapper userMapper;

    @Before
    public void clear() {
        userMapper.deleteAll();
    }

    @Test
    public void useLimit() {
        int i = 0;
        while (i++ < 100) {
            User user = new User("name" + i, i);
            userMapper.insertByUser(user);
        }

        List<User> list = userMapper.getAllByPage(50, 5);
        // 应该就是id = [51, 56]的范围
        i = 51;
        for (User user : list) {
            assert user.getAge() == i++;
        }
    }
}
