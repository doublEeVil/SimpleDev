package com.simpledev.springboot_jdbctemplate_druid.controller;

import com.simpledev.springboot_jdbctemplate_druid.entity.User;
import com.simpledev.springboot_jdbctemplate_druid.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/q/{name}")
    public List<User> q(@PathVariable String  name) {
        return userService.queryByName(name);
    }
}
