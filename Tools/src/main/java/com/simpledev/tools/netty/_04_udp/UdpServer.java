package com.simpledev.tools.netty._04_udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.nio.charset.Charset;
import java.util.List;

public class UdpServer {
    int port;

    public UdpServer(int p) {
        port = p;
    }

    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        // 这里没有使用ServerBootstrap,但实际上也能运行,说明两者之前的差距不大
        Bootstrap b = new Bootstrap();
        b.group(group)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new MsgDecoder());
                        pipeline.addLast(new MsgHandler());
                    }
                })
                .bind(port);
    }

    public static void main(String ... args) throws Exception {
        new UdpServer(8888).start();
    }
}

class Msg {
    String val;
    public String toString() {return val;}
}

class MsgDecoder extends MessageToMessageDecoder<DatagramPacket> {
    @Override
    protected void decode(ChannelHandlerContext ctx, DatagramPacket msg, List<Object> out) throws Exception {
        ByteBuf content = msg.content();
        Msg m = new Msg();
        m.val = content.toString(Charset.defaultCharset());
        out.add(m);
    }
}

class MsgHandler extends SimpleChannelInboundHandler<Msg> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {
        System.out.println("----" + msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("---exception");
        cause.printStackTrace();
    }
}
