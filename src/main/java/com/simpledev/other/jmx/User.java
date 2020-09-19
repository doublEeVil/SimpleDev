package com.simpledev.other.jmx;

import java.util.Map;

public class User implements UserMBean {
    private String name;
    private String addr;
    private int age;
    private Map map;

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setAddr(String addr) {
        this.addr = addr;
    }

    @Override
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String getInfo() {
        return toString();
    }

    @Override
    public void setMap(Map map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", addr='" + addr + '\'' +
                ", age=" + age +
                ", map=" + map +
                '}';
    }

    @Override
    public String doPrin() {
        System.out.println(toString());
        return toString();
    }

    @Override
    public String doSet(String name, String addr, int age) {
        this.name = name;
        this.addr = addr;
        this.age = age;
        return toString();
    }
}
