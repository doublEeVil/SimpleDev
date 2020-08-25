package com.simpledev.springboot_comm.controller;

import com.simpledev.springboot_comm.config.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;

/**
 * 使用@RestController 的写法
 */
@RestController
public class HelloController {

    @Autowired
    private ApplicationContext context;

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping(value = "/hello", method = {RequestMethod.POST})
    public String hello1() {
        return "hello_post";
    }

    @RequestMapping("/abc")
    public String abc(ServletRequest request) {
        System.out.println("----ip:" + request.getRemoteAddr());
        return "abc";
    }


    /**
     * 备注：必须要有get/set方法，不然默认的转换器无法转换成json
     */
    class Info {
        int code;
        String msg;

        public Info(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

    @RequestMapping("/info")
    public Info info(@RequestParam("id") int id, @RequestParam("name") String name) {
        return new Info(200, "id:" + id + " name:" + name);
    }

    /**
     * 特殊用法
     * @return
     */
    @RequestMapping("/other/{name}")
    public String otherInfo(@PathVariable String name) {
        return "path: " + name;
    }

    @GetMapping("/null")
    public String nullException() {
        throw new NullPointerException("");
    }

    @GetMapping("/exception")
    public String exception() {
        throw new RuntimeException();
    }

    @RequestMapping("/test")
    public String test() {
        System.out.println(context.getBean(AppConfig.class));
        return "test";
    }
}
