package com.simpledev.tools.netty._04_udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpClient {
    public static void main(String ... args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        // 持续发送报文
        // UDP是无连接的
        while (true) {
            String s = "dddds3;";
            byte[] data = s.getBytes();
            DatagramPacket send = new DatagramPacket(data, data.length, InetAddress.getByName("127.0.0.1"), 8888);
            socket.send(send);
            Thread.sleep(1000L);
        }
    }
}
