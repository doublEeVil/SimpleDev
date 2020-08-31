package com.simpledev.other.thread_interupted.msg;

public abstract class Msg {
    int header;

    public int getHeader() {
        return header;
    }

    public void setHeader(int header) {
        this.header = header;
    }
}
