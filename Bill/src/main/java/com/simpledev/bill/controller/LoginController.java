package com.simpledev.bill.controller;

import com.simpledev.bill.entity.User;
import com.simpledev.bill.util.JWTUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@SessionAttributes("user")
public class LoginController {
    @Value("${app.user}")
    private String USER;
    @Value("${app.pwd}")
    private String PWD;

    @RequestMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse resp, @RequestParam("user") String user, @RequestParam("pwd") String pwd) throws Exception {
        if (USER.equals(user) && PWD.equals(pwd)) {
            // request.getSession().setAttribute("user", "true");
            User userEntity = new User();
            userEntity.setId(1);
            userEntity.setName(USER);
            userEntity.setPassword(PWD);
            String token = JWTUtil.createToken(userEntity);
            resp.addHeader("authorization", token);
            resp.addCookie(new Cookie("authorization", token));
            resp.getWriter().print(token);
        }
    }
}
