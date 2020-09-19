package com.simpledev.springboot_mongodb.controller;

import com.simpledev.springboot_mongodb.dao.LimitUserDao;
import com.simpledev.springboot_mongodb.dao.UserDao;
import com.simpledev.springboot_mongodb.entity.LimitUser;
import com.simpledev.springboot_mongodb.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class HelloController {

    @Autowired
    private LimitUserDao limitUserDao;
    @Autowired
    private UserDao userDao;

    @GetMapping("/add")
    public LimitUser q() {
        userDao.save(new User());
        //limitUserDao.save(new LimitUser());
        return null;
    }
}
