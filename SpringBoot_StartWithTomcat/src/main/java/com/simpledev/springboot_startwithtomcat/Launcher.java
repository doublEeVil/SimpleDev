package com.simpledev.springboot_startwithtomcat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@SpringBootApplication
@RestController
public class Launcher extends SpringBootServletInitializer {
    public static void main(String ... args) {
        SpringApplication.run(Launcher.class, args);
    }

    // 1.
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return super.configure(builder);
    }

    @RequestMapping("/abc")
    public @ResponseBody
    RetData abc(HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        return new RetData("nnn");
    }

    @RequestMapping("/exp")
    public String exp() {
        throw new RuntimeException("unknown error");
    }

    class RetData {
        String name;

        public RetData(String name) {
            this.name = name;
        }
    }
}


