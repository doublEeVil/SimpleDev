package com.simpledev.springboot_comm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 使用@Controller
 * 和@RestController的区别在于， 返回值必须使用注解
 */

@Controller()
@RequestMapping("/wd")
public class WorldController {

    @RequestMapping("/a")
    public String a() {
        return "abc";
    }

    class Ret {
        String msg;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Ret(String msg) {
            this.msg = msg;
        }
    }


    /**
     * 上面的基础类型不需要@RequestMapping注解
     * 如果是VO类型，必须加上该注解
     */
    @RequestMapping("b")
    public @ResponseBody Ret b() {
        return new Ret("world");
    }
}
