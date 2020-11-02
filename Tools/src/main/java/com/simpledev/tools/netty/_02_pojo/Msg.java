package com.simpledev.tools.netty._02_pojo;

public class Msg {
    int id;
    String msg;
    long create;

    @Override
    public String toString() {
        return "Msg{" +
                "id=" + id +
                ", msg='" + msg + '\'' +
                ", create=" + create +
                '}';
    }
}
