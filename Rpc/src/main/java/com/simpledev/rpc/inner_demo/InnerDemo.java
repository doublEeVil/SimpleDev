package com.simpledev.rpc.inner_demo;

import com.simpledev.rpc.inner_demo.client.InnerDemoClient;
import com.simpledev.rpc.inner_demo.protocol.HelloAService;

public class InnerDemo {

    public static void main(String ... args) {
        InnerDemoClient client = new InnerDemoClient();
        System.out.println(client.getService(HelloAService.class).div(2, 0));
    }
}
