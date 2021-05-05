package com.simpledev.bill.controller;

import com.simpledev.bill.exception.BillException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class DefaultExceptionController {

    @ResponseBody()
    @ExceptionHandler(value = BillException.class)
    public void onBillException(HttpServletRequest request, HttpServletResponse response, BillException e) {
        if (BillException.NO_LOGIN == e.getCode()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseBody()
    @ExceptionHandler(value = Exception.class)
    public void onException(HttpServletRequest request, HttpServletResponse response, Exception e) {
        e.printStackTrace();
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
}
