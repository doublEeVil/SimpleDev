package com.simpledev.other.jmx;

import java.util.Map;

public interface UserMBean {
    void setName(String name);
    void setAddr(String addr);
    void setAge(int age);

    String getInfo();

    void setMap(Map map);

    String doPrin();

    String doSet(String name, String addr, int age);
}
