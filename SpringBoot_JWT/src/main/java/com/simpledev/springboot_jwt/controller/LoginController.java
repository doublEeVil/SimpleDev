package com.simpledev.springboot_jwt.controller;

import com.simpledev.springboot_jwt.entity.User;
import com.simpledev.springboot_jwt.util.JWTUtil;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.Map;

@RestController

public class LoginController {

    static Map<String, User> userMap = new HashMap<>();

    static {
        User user1 = new User();
        user1.setId(1);
        user1.setName("AAA");
        user1.setPassword("123456");
        userMap.put(user1.getName(), user1);
    }

    @RequestMapping("/login/{name}")
    public String login(@PathVariable("name") String name, @PathParam("pwd") String pwd) {
        User user = userMap.get(name);
        if (user != null && user.getPassword().equals(pwd)) {
            return JWTUtil.createToken(user);
        }
        return "错误，请稍后再试";
    }
}
