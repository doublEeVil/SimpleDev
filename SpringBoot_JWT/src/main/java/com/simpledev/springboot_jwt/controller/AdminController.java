package com.simpledev.springboot_jwt.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/")
public class AdminController {

    @RequestMapping("/info")
    public String info() {
        return Thread.currentThread().getName();
    }
}
