package com.simpledev.tools.netty._01;

import com.google.common.collect.Lists;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;


public class SimpleServer {
    private int port;

    public SimpleServer(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(port)
                    .childHandler((new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new SimpleHandler());
                        }
                    }));
            ChannelFuture future = b.bind().sync();
            future.channel().closeFuture().sync();
        }  finally {
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String ... args) throws Exception {
        // new SimpleServer(8888).start();
        class MyEventLoop extends DefaultEventLoop {
            @Override
            protected void run() {
                for (;;) {
                    try {
                        Runnable task = takeTask();
                        if (task != null) {
                            task.run();
                            updateLastExecutionTime();
                        }
                        if (confirmShutdown()) {
                            break;
                        }
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        int threads = 5;
        MyEventLoop[] loops = new MyEventLoop[threads];
        for (int i = 0; i < threads; i++) {
            loops[i] = new MyEventLoop();
        }
        class User {
            int id;
            int cnt;
            public String toString() {
                return "id:" + id + " cnt:" + cnt;
            }
        }
        // 模拟10个人
        User[] users = new User[10];
        for (int i = 0; i < 10; i++) {
            users[i] = new User();
            users[i].id = i;
        }
        ExecutorService jdkPool = Executors.newFixedThreadPool(5);

        for (int i = 10000; i > 0; i--) {
            // 分配eventloop
            User user = users[i % 10];
            final int j = i;
            Runnable run = () -> {
                try {
                    int rand = j % 3;
                    Thread.sleep(rand);
                } catch (Exception e) {

                }
                user.cnt++;
            };
            // MyEventLoop loop =loops[user.id % threads];
            // loop.execute(()->user.cnt++);
            jdkPool.execute(run);
        }
        Thread.sleep(7000L);
        for (int i = 0; i < 10; i++) {
            User user = users[i % 10];
            System.out.println(user);
        }
    }
}

@ChannelHandler.Sharable
class SimpleHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        int i = 0;
        System.out.println("---Thread:" + Thread.currentThread());
        System.out.println(1/i);
        System.out.printf("server rcv: %s\n", buf.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
