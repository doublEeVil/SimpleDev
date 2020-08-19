package com.simpledev.rpc.inner_demo.server;

import com.simpledev.rpc.inner_demo.comm.Common;
import com.simpledev.rpc.inner_demo.packet.RpcRequest;
import com.simpledev.rpc.inner_demo.packet.RpcResponse;
import com.simpledev.rpc.inner_demo.server.impl.HelloAServiceImpl;
import com.simpledev.rpc.inner_demo.server.impl.HelloBServiceImpl;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InnerDemoServer {
    Map<Integer, MethodWrapper> protocolMap = new HashMap<>();

    public InnerDemoServer() {
        init();
    }

    private void init() {
        register();
    }

    private void register() {
        List<Object> list = Arrays.asList(new HelloAServiceImpl(), new HelloBServiceImpl());
        for (Object obj : list) {
            Class<?> clz = obj.getClass();
            if (clz.getInterfaces() == null) {
                continue;
            }
            if (clz.getInterfaces().length != 1) {
                continue;
            }

            Class<?> interfaceClz = clz.getInterfaces()[0];
            Method[] methods = interfaceClz.getMethods();
            for (Method method : methods) {
                int protocolId = Common.getProtocolId(interfaceClz, method);
                MethodWrapper wrapper = new MethodWrapper(obj, method);
                protocolMap.put(protocolId, wrapper);
                System.out.println("---register: " + method.getName());
            }
        }
    }

    public RpcResponse call(RpcRequest request) {
        if (!protocolMap.containsKey(request.protocolId)) {
            return RpcResponse.ofErr(request.id, 404, "not found");
        }
        try {
            MethodWrapper wrapper = protocolMap.get(request.protocolId);
            Object ret = wrapper.method.invoke(wrapper.target, request.params);
            return RpcResponse.ofSuccess(request.id, ret);
        } catch (Exception e) {
            return RpcResponse.ofErr(request.id, 500, e.getMessage());
        }
    }
}

class MethodWrapper {
    Object target;
    Method method;

    public MethodWrapper(Object target, Method method) {
        this.target = target;
        this.method = method;
    }
}
