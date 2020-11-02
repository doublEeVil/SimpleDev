package com.simpledev.tools.netty._05_udp_broadcast;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.nio.charset.Charset;
import java.util.List;

public class Receiver1 {

    public void start() {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new MsgPOJODecoder());
                        pipeline.addLast(new MsgPOJOHandler());
                    }
                })
                .bind(8888);

    }

    public static void main(String ... args) {
        new Receiver1().start();
    }
}

class MsgPOJODecoder extends MessageToMessageDecoder<DatagramPacket> {
    @Override
    protected void decode(ChannelHandlerContext ctx, DatagramPacket msg, List<Object> out) throws Exception {
        MsgPOJO msgPOJO = new MsgPOJO();
        ByteBuf buf = msg.content();
        msgPOJO.id = buf.readInt();
        String m = buf.readCharSequence(buf.readableBytes(), Charset.defaultCharset()).toString();
        msgPOJO.msg = m;
        //msgPOJO.msg = buf.toString(buf.readerIndex(), buf.readableBytes() + buf.readerIndex(), Charset.defaultCharset());
        out.add(msgPOJO);
    }
}

class MsgPOJOHandler extends SimpleChannelInboundHandler<MsgPOJO> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MsgPOJO msg) throws Exception {
        System.out.println("----rdv: " + msg);
    }
}
