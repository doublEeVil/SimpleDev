package com.simpledev.springboot_jdbctemplate;

import com.simpledev.springboot_jdbctemplate.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Before
    public void deleteAll() {
        userService.deleteAll();
    }

    @Test
    public void test() {
        userService.create("AAA", 12);
        userService.create("AAA", 13);
        userService.create("AAA", 14);
        userService.create("BBB", 15);

        Assert.assertEquals(3, userService.queryByName("AAA").size());
        Assert.assertEquals(15, userService.queryByName("BBB").get(0).getAge());

    }
}
