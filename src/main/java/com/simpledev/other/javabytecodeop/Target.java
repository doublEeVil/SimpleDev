package com.simpledev.other.javabytecodeop;

import javax.persistence.Id;
import javax.persistence.Transient;

public class Target {
    private int id;

    private transient String name;

    @Deprecated
    public int getId() {
        return id;
    }

    @Transient
    @Id
    public void setId() {

    }
}
