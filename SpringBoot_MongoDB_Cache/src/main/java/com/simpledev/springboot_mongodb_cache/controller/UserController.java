package com.simpledev.springboot_mongodb_cache.controller;

import com.simpledev.springboot_mongodb_cache.dao.UserDao;
import com.simpledev.springboot_mongodb_cache.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/")
public class UserController {
    @Autowired
    private UserDao userDao;

    @RequestMapping("/q/{name}")
    public User q(@PathVariable String name) {
        return userDao.findByName(name);
    }
}
