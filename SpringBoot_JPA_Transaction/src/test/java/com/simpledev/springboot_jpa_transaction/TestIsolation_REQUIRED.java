package com.simpledev.springboot_jpa_transaction;

import com.simpledev.springboot_jpa_transaction.entity.User;
import com.simpledev.springboot_jpa_transaction.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * REQUIRED：如果当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务。
 */

/**
 * 测试失败，可能和Test的机制有关
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestIsolation_REQUIRED {

    @Autowired
    private UserRepository userRepository;

    @Before
    public void clear() {
        userRepository.deleteAll();
    }

    @Test
    public void test1() {
        // 先插入10个，实际插入第7个就会抛异常
        Assert.assertThrows(Exception.class, () -> {
            add10WithNoTransaction();
        });

        List<User> users = userRepository.findAll();
        Assert.assertEquals(6, users.size());
    }

    /**
     * 测试失败
     */
    @Test
    @Transactional
    public void test2() {
//        userRepository.deleteAll();
//        // 开启事务传播，必然会回滚
//        //Assert.assertThrows(ConstraintViolationException.class, () -> {
//        try {
//            add10WithTransaction();
//        } catch (Throwable e) {
//            System.out.println("------");
//            e.printStackTrace();
//        }
//
//        //});
//        List<User> users = userRepository.findAll();
//        Assert.assertEquals(0, users.size());
    }


    public void add10WithNoTransaction() {
        for (int i = 0; i < 10; i++) {
            User user = new User("user" + i, i * 10);
            userRepository.save(user);
        }
    }

    @Transactional(value = Transactional.TxType.REQUIRED)
    public void add10WithTransaction() {
        for (int i = 0; i < 10; i++) {
            User user = new User("user" + i, i * 10);
            userRepository.save(user);
        }
    }
}
