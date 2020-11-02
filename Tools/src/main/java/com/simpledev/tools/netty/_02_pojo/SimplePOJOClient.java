package com.simpledev.tools.netty._02_pojo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.*;
import java.net.Socket;

/**
 * 客户端，用来发送和处理数据
 */
public class SimplePOJOClient {

    public static void main(String ... args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 8888);



        new Thread(() -> {
            int id = 0;
            while (true) {
                try {
                    Msg msg1 = new Msg();
                    msg1.id = id++;
                    msg1.msg = "hhh,1";
                    msg1.create = System.currentTimeMillis();

                    Msg msg2 = new Msg();
                    msg2.id = id++;
                    msg2.msg = "hhh,2";
                    msg2.create = System.currentTimeMillis();

                    OutputStream out = socket.getOutputStream();
                    DataOutputStream w = new DataOutputStream(out);


                    byte[] head = new byte[]{'j', 'a', 'v', 'a'};
                    w.write(head);
                    w.flush();

                    byte[] strData = msg1.msg.getBytes();
                    w.writeInt(4+4+strData.length+8);
                    w.writeInt(msg1.id);
                    w.writeInt(strData.length);
                    w.write(strData);
                    w.flush();
                    w.writeLong(msg1.create);
                    w.flush(); // 这一步，相当于已经完成了消息1的发送

                    w.write(head);
                    w.flush();

                    strData = msg2.msg.getBytes();
                    w.writeInt(4+4+strData.length+8);
                    w.writeInt(msg2.id);
                    w.writeInt(strData.length);
                    w.write(strData);
                    w.flush();
                    w.writeLong(msg2.create);
                    w.flush(); // 这一步，相当于已经完成了消息2的发送

                    // 发送一个垃圾消息
                    String s = "javahsdfhhsdf";
                    w.write(s.getBytes());

                    Thread.sleep(1000L);
                } catch (Exception e) {

                }
            }
        }).start();

        new Thread(() -> {
           while (true) {
               try {
                   InputStream in = socket.getInputStream();
                   DataInputStream r = new DataInputStream(in);

                   while (true) {
                       System.out.println(r.readUTF());
                   }

               } catch (Exception e) {

               }
           }
        });

    }
}
