package com.simpledev.tools.netty._05_udp_broadcast;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.List;

public class Broadcaster {

    public void start() {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                // 感觉可以不要用handler
                .handler(new MsgPOJOEncoder())
                ;

        Channel channel = b.bind(0).channel();
        int id = 0;
        while (true) {
            MsgPOJO msgPOJO = new MsgPOJO();
            msgPOJO.id = id++;
            msgPOJO.msg = "ddddss" + System.currentTimeMillis();
            channel.writeAndFlush(msgPOJO);
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("---se");
        }
    }

    public static void main(String ... args) {
        new Broadcaster().start();
    }
}

class MsgPOJOEncoder extends MessageToMessageEncoder<MsgPOJO> {
    @Override
    protected void encode(ChannelHandlerContext ctx, MsgPOJO msg, List<Object> out) throws Exception {
        // 这一步把消息转为udp报文
        ByteBuf buf = ctx.alloc().buffer(4);
        buf.writeInt(msg.id);
        buf.writeCharSequence(msg.msg, Charset.defaultCharset());
        DatagramPacket packet = new DatagramPacket(buf, new InetSocketAddress("255.255.255.255", 8888));
        out.add(packet);
    }
}
