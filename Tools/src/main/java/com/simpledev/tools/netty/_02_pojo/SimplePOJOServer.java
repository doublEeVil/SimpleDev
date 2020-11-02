package com.simpledev.tools.netty._02_pojo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.concurrent.ImmediateEventExecutor;

import java.nio.charset.Charset;
import java.util.List;

/**
 * 模拟下得到数据然后链式传递下去
 */
public class SimplePOJOServer {

    public void start() {
        final EventLoopGroup group = new NioEventLoopGroup(5);
        final ServerBootstrap b = new ServerBootstrap();
        final ChannelGroup channelGroup = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE); // 用来群发是个好东西
        b.group(group)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new MsgDecoder());
                        pipeline.addLast(new MsgHandler1());
                        pipeline.addLast(new MsgHandler2(channelGroup));
                        pipeline.addLast(new MsgHandler3());
                    }
                })
                .bind(8888);
    }

    public static void main(String ... args) {
        new SimplePOJOServer().start();
    }
}

class MsgDecoder extends ByteToMessageDecoder {
    private ByteBuf last;
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 对于同一个Channel来说，理论上应该是同一个in
        if (last == null) {
            last = in;
        } else {
            // System.out.println("是否是同一个：" + (last == in));
            last = in;
        }
        // 假设消息是这样的结构固定头[java] + len + 消息体

        System.out.println();
        ByteBuf buf = in.copy();
        while (buf.readableBytes() > 0) {
            System.out.print(" " + buf.readByte());
        }
        System.out.print("\n");

        byte[] data = new byte[] {'j', 'a', 'v', 'a'};
        if (in.readableBytes() < 4 + 4) {
            // 说明不全
            in.retain();
        } else {
            in.markReaderIndex(); //
            for (int i = 0; i < 4; i++) {
                if (in.readByte() != data[i]) {
                    in.skipBytes(in.readableBytes());
                    return;
                }
            }
            int len = in.readInt();
            if (len > 1000) {
                in.skipBytes(in.readableBytes());
                return;
            }
            if (in.readableBytes() < len) {
                in.retain();
                in.resetReaderIndex(); //
            } else {
                Msg msg = new Msg();
                msg.id = in.readInt();
                int sLen = in.readInt();
                if (sLen > 1000) {
                    in.skipBytes(in.readableBytes());
                    return;
                }
                byte[] st = new byte[sLen];
                in.readBytes(st);
                msg.msg = new String(st);
                msg.create = in.readLong();
                out.add(msg);
            }
        }
    }
}

class MsgHandler1 extends SimpleChannelInboundHandler<Msg> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {
        // 可以明显看到只会处理到这一步
        System.out.println("----handler 111 msg:" + msg);
        if (msg.id % 3 == 2) {
            // 让handler2也继续处理
            ctx.fireChannelRead(msg);
        }
    }
}

class MsgHandler2 extends SimpleChannelInboundHandler<Msg> {

    ChannelGroup group;

    public MsgHandler2(ChannelGroup group) {
        this.group = group;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {
        System.out.println("----handler 222 msg:" + msg);
        group.add(ctx.channel());
        group.writeAndFlush(Unpooled.copiedBuffer("heloo123", Charset.defaultCharset()));
    }
}

class MsgHandler3 extends SimpleChannelInboundHandler<Msg> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {
        System.out.println("----handler 333 msg:" + msg);
    }
}
