package com.simpledev.spring_boot_jpa;

import com.simpledev.spring_boot_jpa.entity.User;
import com.simpledev.spring_boot_jpa.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JpaTest {

    @Autowired
    private UserService userService;

    @Before
    public void clear() {
        userService.deleteAll();
    }

    @Test
    public void test() {
        userService.save(new User("AA", 12));
        userService.save(new User("AA", 12));
        userService.save(new User("AA", 13));
        userService.save(new User("BB", 13));

        Assert.assertEquals(4, userService.count());

        Assert.assertEquals(3, userService.findByName("AA").size());

        Assert.assertEquals(2, userService.findByNameAndAge("AA", 12).size());

        Assert.assertEquals(3, userService.findUser("AA").size());

        Assert.assertEquals(2, userService.findUser(1).size());
    }
}
