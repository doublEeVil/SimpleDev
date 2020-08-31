package com.simpledev.springboot_jpa_transaction.service;

import com.simpledev.springboot_jpa_transaction.entity.User;
import com.simpledev.springboot_jpa_transaction.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    //****************************************************************
    /**
     * REQUIRED 如果当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务。
     */

    @Transactional
    public void required_1() {
        required();
    }

    public void required_2() {
        required();
    }

    // 默认是这个
    @Transactional(propagation = Propagation.REQUIRED)
    public void required() {
        for (int i = 0; i < 10; i++) {
            User user = new User("name" + i, i * 10);
            userRepository.save(user);
        }
    }

    //*****************************************************************
    /**
     * SUPPORTS：如果当前存在事务，则加入该事务；如果当前没有事务，则以非事务的方式继续运行。
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public void supports() {
        for (int i = 0; i < 10; i++) {
            User user = new User("name" + i, i * 10);
            userRepository.save(user);
        }
    }

    public void supports1() {
        supports();
    }

    @Transactional
    public void supports2() {
        supports();
    }

    //*****************************************************************
    /**
     * MANDATORY：如果当前存在事务，则加入该事务；如果当前没有事务，则抛出异常。
     */
    @Transactional(propagation = Propagation.MANDATORY)
    public void mandatory() {
        User user = new User("name", 10);
        userRepository.save(user);
    }

    //**************************************
    // REQUIRES_NEW：创建一个新的事务，如果当前存在事务，则把当前事务挂起


    //**************************************
    // NOT_SUPPORTED：以非事务方式运行，如果当前存在事务，则把当前事务挂起。
    @Transactional
    public void notSupported() {
        notSupported1();
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void notSupported1() {
        for (int i = 0; i < 10; i++) {
            User user = new User("name" + i, i * 10);
            userRepository.save(user);
        }
    }

    //**************************************
    // NEVER：以非事务方式运行，如果当前存在事务，则抛出异常。

    @Transactional
    public void never() {
        never1();
    }

    @Transactional(propagation = Propagation.NEVER)
    public void never1() {
        User user = new User("name", 10);
        userRepository.save(user);
    }

    //***************************************
    // NESTED：如果当前存在事务，则创建一个事务作为当前事务的嵌套事务来运行；如果当前没有事务，则该取值等价于REQUIRED。
}
