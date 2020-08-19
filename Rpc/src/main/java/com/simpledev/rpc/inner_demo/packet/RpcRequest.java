package com.simpledev.rpc.inner_demo.packet;

public class RpcRequest {
    public long id;         // 请求id
    public int protocolId;  // 协议号
    public Object[] params; // 参数
}
