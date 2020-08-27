package com.simpledev.springboot_jdbctemplate.controller;

import com.simpledev.springboot_jdbctemplate.entity.User;
import com.simpledev.springboot_jdbctemplate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/q")
    public User q(@RequestParam int id) {
        return null;
    }
}
