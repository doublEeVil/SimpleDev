package com.simpledev.springboot_mongodb;

import com.simpledev.springboot_mongodb.dao.LimitUserDao;
import com.simpledev.springboot_mongodb.dao.UserDao;
import com.simpledev.springboot_mongodb.entity.CommObj;
import com.simpledev.springboot_mongodb.entity.LimitUser;
import com.simpledev.springboot_mongodb.entity.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ConcurrentHashMap;


@SpringBootTest
@RunWith(SpringRunner.class)
public class MongoTest {

    @Autowired
    private UserDao userDao;
    @Autowired
    private LimitUserDao limitUserDao;

    @Before
    public void onClear() {
        userDao.deleteAll();
        limitUserDao.deleteAll();
    }

    @Test
    public void test() {
        User user = new User();
        user.setId(10001);
        user.setName("AAA");
        user.setAge(23);

        userDao.save(user);

        User find = userDao.findById(10001);
        Assert.assertEquals(find, user);

        find = userDao.findByName("AAA");
        Assert.assertEquals(find, user);

        find = userDao.findByNameAndAge("AAA", 23);
        Assert.assertEquals(find, user);

        // update
        user.setAge(26);
        user.setName("BBB");
        userDao.update(user);

        find = userDao.findByName("AAA");
        Assert.assertNull(find);

        find = userDao.findByName("BBB");
        Assert.assertEquals(26, find.getAge());

        // delete
        userDao.delete(find);
        find = userDao.findById(10001);
        Assert.assertNull(find);

        // 从这里可以看出，删除是根据id来进行识别的
        userDao.save(user);
        user.setName("CCC");
        user.setAge(33);
        userDao.delete(user);

        find = userDao.findById(10001);
        Assert.assertNull(find);

        // id重复
        userDao.save(user);
        // 没有抛异常，反而是直接替换了，一个隐形大坑
        // Assert.assertThrows(Exception.class, () -> userDao.save(user));

        user.setName("DDD");
        userDao.save(user);
        find = userDao.findById(10001);
        Assert.assertEquals("DDD", find.getName());
    }

    @Test
    public void testConverter() {
        LimitUser limitUser = new LimitUser();
        limitUser.setId(125);
        limitUser.setAge(23);
        limitUser.setAddress("beijing");
        limitUser.setName("bei");
        CommObj info  = new CommObj();
        info.setP1("1111");
        info.setP2("2222");
        limitUser.setInfo(info);
        limitUser.setName2("name2");
        limitUser.setMap(new ConcurrentHashMap());

        limitUserDao.save(limitUser);

        LimitUser find = limitUserDao.find(124);
        Assert.assertEquals(limitUser.getAddress(), find.getAddress());
    }

    @Test
    public void test3() {
        LimitUser find = limitUserDao.find(125);
        System.out.println(find.getInfo().getP1());
    }
}
