package com.simpledev.springboot_cache;

import com.simpledev.springboot_cache.dao.UserRepository;
import com.simpledev.springboot_cache.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CacheTest {
    @Autowired
    protected UserRepository userRepository;

    @Autowired
    private CacheManager cacheManager;


    /**
     * 可以看到在没有update的情况下, hashcode不会变化，但一更新，hashcode值就变化了
     */
    @Test
    public void test() {
        User user = new User("AAA", 24);
        userRepository.save(user);
        System.out.println("----最原始的user:" + user + " " + user.hashCode());

        User user1 = userRepository.findByName("AAA");
        Assert.assertEquals(user1.getAge(), 24);
        System.out.println("----第一次查询：" + user1+ " " + user1.hashCode());

        user1 = userRepository.findByName("AAA");
        Assert.assertEquals(user1.getAge(), 24);
        System.out.println("----第二次查询：" + user1+ " " + user1.hashCode());

        user1.setAge(25);
        onPut(user1);

        user1 = userRepository.findByName("AAA");
        System.out.println("----第三次查询：" + user1+ " " + user1.hashCode());
        Assert.assertEquals(25, user1.getAge());

//        onDelete(user1);
//        user1 = userRepository.findByName("AAA");
//        Assert.assertEquals(null, user1);
    }

    @CachePut
    public void onPut(User user) {
        userRepository.save(user);
    }

    // 失败，没有走缓存。。
//    @CacheEvict
//    public void onDelete(User user) {
//        userRepository.delete(user);
//    }
}
