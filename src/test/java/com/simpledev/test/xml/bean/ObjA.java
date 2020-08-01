package com.simpledev.test.xml.bean;

import java.util.List;

public class ObjA {
    private long id;
    private String name;
    private int age;
    private List<ObjB> infoList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<ObjB> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<ObjB> infoList) {
        this.infoList = infoList;
    }
}
