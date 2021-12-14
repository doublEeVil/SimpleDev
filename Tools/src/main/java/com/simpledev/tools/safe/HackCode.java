package com.simpledev.tools.safe;

public class HackCode {
    static {
        try {
            Runtime.getRuntime().exec("calc");
        } catch (Exception e) {
        }
    }
}
