package com.simpledev.netty_batch_request;

import com.simpledev.netty_batch_request.http.Dispatcher;
import com.simpledev.netty_batch_request.http.HttpAction;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class NettyBatchRequestApp {

    public static void main(String ... args) throws IOException {
        Dispatcher dispatcher = new Dispatcher(12);
        ConcurrentHashMap<HttpAction, HttpAction> map = new ConcurrentHashMap<>();
        new Thread(() -> {
            int i = 100;
            while (i-- > 0) {
                try {
                    Thread.sleep(1000L);
                    for (int j = 0; j < 100; j++) {
                        HttpAction action = new HttpAction(dispatcher);
                        map.put(action, action);
                        action.sendHttpRequest("http://127.0.0.1:8888/hello");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(3000L);
                    int cnt = 0;
                    for (HttpAction action : map.keySet()) {
                        if (action.isActive()) {
                            cnt++;
                        }
                    }
                    System.out.println("----当前连接：" + cnt);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
