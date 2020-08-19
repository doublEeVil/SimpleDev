package com.simpledev.rpc.inner_demo.client;

import com.simpledev.rpc.inner_demo.comm.Common;
import com.simpledev.rpc.inner_demo.packet.RpcRequest;
import com.simpledev.rpc.inner_demo.packet.RpcResponse;
import com.simpledev.rpc.inner_demo.server.InnerDemoServer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class InnerDemoClient {

    Map<Class, Object> objectMap = new HashMap<>();

    public InnerDemoClient() {
        init();
    }

    private void init() {

    }

    public <T> T getService(Class<T> clz) {
        if (objectMap.containsKey(clz)) {
            return (T) objectMap.get(clz);
        }

        T obj = (T) Proxy.newProxyInstance(this.getClass().getClassLoader(),
                new Class[]{clz}, new InvokeHandler(clz));
        objectMap.put(clz, obj);
        return obj;
    }
}

class InvokeHandler implements InvocationHandler {
    static InnerDemoServer server = new InnerDemoServer();
    static int inc = 0;

    Class clz;

    public InvokeHandler(Class clz) {
        this.clz = clz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        int protocolId = Common.getProtocolId(clz, method);
        RpcRequest request = new RpcRequest();
        request.id = inc++;
        request.protocolId = protocolId;
        request.params = args;
        RpcResponse response = server.call(request);
        if (response.code == 200) {
            return response.result;
        }
        throw new RuntimeException(response.result + "");
    }
}
