package com.simpledev.bill.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillException extends RuntimeException {
    public static int NO_LOGIN = 0;
    public static int ERROR = 1;

    private int code;
    private String msg;

    public BillException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
