package com.simpledev.stonelang._01;

import java.io.IOException;

public class ParseException extends Exception {
    public ParseException(Token token) {
        this("", token);
    }

    public ParseException(String msg, Token token) {
        super("syntax error around " + location(token) + ". " + msg);
    }

    public static String location(Token token) {
        return null;
    }

    public ParseException(IOException e) {
        super(e);
    }

    public ParseException(String cause) {
        super(cause);
    }
}
