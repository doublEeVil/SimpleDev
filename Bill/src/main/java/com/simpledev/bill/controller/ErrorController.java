package com.simpledev.bill.controller;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

// filter中的异常无法在@ControllerAdvice捕获，因此只能再建一个类
@RestController
public class ErrorController extends BasicErrorController {
    public ErrorController() {
        super(new DefaultErrorAttributes(), new ErrorProperties());
    }

    @RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        // 过滤器错误只能是未登陆
        Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
        HttpStatus status = getStatus(request);
        status = HttpStatus.valueOf(HttpServletResponse.SC_UNAUTHORIZED);
        //自定义的错误信息类
        //status.value():错误代码，
        //body.get("message").toString()错误信息
        //TokenException Filter抛出的自定义错误类
        return new ResponseEntity<>(status);
    }
}
