package com.simpledev.rpc.inner_demo.packet;

public class RpcResponse {
    public long id;
    public int code;       // 状态码
    public Object result;   // 如果状态码异常，返回异常信息

    public static RpcResponse ofErr(long id, int code, String msg) {
        RpcResponse resp = new RpcResponse();
        resp.id = id;
        resp.code = code;
        resp.result = msg;
        return resp;
    }

    public static RpcResponse ofSuccess(long id, Object result) {
        RpcResponse resp = new RpcResponse();
        resp.id = id;
        resp.code = 200;
        resp.result = result;
        return resp;
    }
}
