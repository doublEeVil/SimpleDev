package com.simpledev.springboot_jpa_transaction.controller;

import com.simpledev.springboot_jpa_transaction.entity.User;
import com.simpledev.springboot_jpa_transaction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 事务传播测试，比较奇怪的在于spring 和 javax.包下都定义了Transactional注解，但spring比javax多了一个NESTED
 * 如果用javax包下的注解，会发生很多难以理解的问题
 *
 * 出现了很多问题，先暂停测试
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/get_all")
    public List<User> getAll() {
        return userService.findAll();
    }

    @GetMapping("/delete_all")
    public List<User> deleteAll() {
        userService.deleteAll();
        return getAll();
    }

    //*********************
    // REQUIRED 如果当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务
    // 因为事务的存在，所以必定没有存入任何数据
    @GetMapping("/t11")
    public List<User> testRequired1() {
        // 预想发生异常，不会插入任何数据
        userService.required_1();
        return userService.findAll();
    }

    @GetMapping("/t12")
    public List<User> testRequired2() {
        // 预想发生异常，不会插入任何数据
        userService.required_2();
        return userService.findAll();
    }

    //************************
    // SUPPORTS：如果当前存在事务，则加入该事务；如果当前没有事务，则以非事务的方式继续运行。
    @GetMapping("/t21")
    public List<User> testSupports1() {
        // 预想插入了部分数据
        userService.supports1();
        return userService.findAll();
    }

    @GetMapping("/t22")
    public List<User> testSupports2() {
        // 预想已经发生了回滚
        userService.supports2();
        return userService.findAll();
    }

    //***********************************
    // MANDATORY：如果当前存在事务，则加入该事务；如果当前没有事务，则抛出异常。
    @GetMapping("/t3")
    public List<User> testMandatory() {
        // 预想发生了异常
        userService.mandatory();
        return userService.findAll();
    }

    //**************************************
    // REQUIRES_NEW：创建一个新的事务，如果当前存在事务，则把当前事务挂起


    //**************************************
    // NOT_SUPPORTED：以非事务方式运行，如果当前存在事务，则把当前事务挂起。
    @GetMapping("/t5")
    public List<User> testNotSupport() {
        // 预想发生异常，只插入部分数据
        userService.notSupported();
        return userService.findAll();
    }

    //**************************************
    // NEVER：以非事务方式运行，如果当前存在事务，则抛出异常。
    @GetMapping("/t6")
    public List<User> testNever() {
        // 预想抛异常
        userService.never();
        return userService.findAll();
    }

    //**************************************
    // NESTED：如果当前存在事务，则创建一个事务作为当前事务的嵌套事务来运行；如果当前没有事务，则该取值等价于REQUIRED。
}
