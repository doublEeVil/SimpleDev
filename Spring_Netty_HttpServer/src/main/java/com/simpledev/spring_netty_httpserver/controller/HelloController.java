package com.simpledev.spring_netty_httpserver.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping({"/hello"})
public class HelloController {
    @RequestMapping(value = "/a")
    public
    @ResponseBody
    JSONObject a(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        json.put("code", 1);
        return json;
    }
}
