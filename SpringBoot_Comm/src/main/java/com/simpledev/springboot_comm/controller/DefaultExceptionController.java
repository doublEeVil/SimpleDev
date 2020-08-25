package com.simpledev.springboot_comm.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常拦截器，可以捕获自己定义的各种异常
 */
@ControllerAdvice
public class DefaultExceptionController {

    @ResponseBody()
    @ExceptionHandler(value = NullPointerException.class)
    public RespErr onNullPointException(HttpServletRequest request, NullPointerException e) {
        return new RespErr(500, "空指针异常");
    }

    @ResponseBody()
    @ExceptionHandler(value = Exception.class)
    public RespErr onNullPointException(HttpServletRequest request,Exception e) {
        return new RespErr(500, "未知异常");
    }
}

class RespErr {
    int code;
    String msg;

    public RespErr(int code, String msg) {
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
