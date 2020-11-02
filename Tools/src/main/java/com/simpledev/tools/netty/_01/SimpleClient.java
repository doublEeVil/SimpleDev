package com.simpledev.tools.netty._01;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.*;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.util.function.BiConsumer;

public class SimpleClient {
    public static void main(String ... args) throws Exception {
        new SimpleClient().start();
    }

    public void start() throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();
        AttributeKey<String> key = AttributeKey.newInstance("KEY");
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress("127.0.0.1", 8888) // 远程地址
                    .attr(key, "FIRST_CHANNEL") // 设置一个属性值
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline().addLast(new ClientHandler());
                        }
                    });
            ChannelFuture future = b.connect().sync();
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }
}

class ClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,netty", CharsetUtil.UTF_8));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf in) throws Exception {
        System.out.printf("rcv server send: %s", in.toString(CharsetUtil.UTF_8));
    }
}
