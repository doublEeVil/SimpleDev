package com.simpledev.spring_netty_httpserver;

import com.simpledev.spring_netty_httpserver.netty.DispatcherServletChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.logging.Logger;

public class NettyHttpServer {
    private static final Logger LOGGER = Logger
            .getLogger(NettyHttpServer.class.getName());

    private int port;

    public NettyHttpServer(int port) {
        this.port = port;
    }

    private ServerBootstrap server = new ServerBootstrap();
    private NioEventLoopGroup workerGroup = new NioEventLoopGroup();
    private NioEventLoopGroup bossGroup = new NioEventLoopGroup();


    public void start() throws Exception {
        try {
            server.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(port)
                    .childHandler(DispatcherServletChannelInitializer.getInstance());

            LOGGER.info("Netty server has started on port : " + port);
            System.out.println("server has started : " + port);
            server.bind().sync().channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
    protected void shutdown() {
        this.bossGroup.shutdownGracefully();
        this.workerGroup.shutdownGracefully();
    }

    public static void main(String ... args) throws Exception {
        new NettyHttpServer(8888).start();
    }
}
