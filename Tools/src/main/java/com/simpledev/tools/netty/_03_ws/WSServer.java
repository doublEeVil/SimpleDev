package com.simpledev.tools.netty._03_ws;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WSServer {

    private int port;

    public WSServer(int p) {
        port = p;
    }

    public void start() {
        EventLoopGroup group = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        b.group(group)
                .channel(NioServerSocketChannel.class)
                // 与handler的区别在于一个初始化就有，一个连接有
                .childHandler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new HttpServerCodec());
                        pipeline.addLast(new ChunkedWriteHandler());
                        pipeline.addLast(new HttpObjectAggregator(64 * 1024));
                        // 可以把WebSocketServerProtocolHandler放到前面，这样的话，如果前面处理了，后续就不再处理升级。不然还需要手动处理升级
                        // 也就是还需继续调用ctx.fireChannelRead(msg.retain());
                        pipeline.addLast(new MyHttpRequestHandler());
                        pipeline.addLast(new WebSocketServerProtocolHandler("/ws", null, true));
                        pipeline.addLast(new MyWSFrameHandler());
                    }
                })
                .bind(port);

    }

    public static void main(String ... args) {
        new WSServer(8888).start();
    }
}

class MyHttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    public static final  String  MSG = "<html>\n" +
            "<body>\n" +
            "hhhhh\n" +
            "<script>\n" +
            "    var ws = new WebSocket(\"ws://127.0.0.1:8888/ws\");\n" +
            "\n" +
            "    ws.onopen = function(evt) {\n" +
            "      console.log(\"Connection open ...\");\n" +
            "      ws.send(\"Hello WebSockets!\");\n" +
            "    };\n" +
            "\n" +
            "    ws.onmessage = function(evt) {\n" +
            "      console.log( \"Received Message: \" + evt.data);\n" +
            "      ws.close();\n" +
            "    };\n" +
            "\n" +
            "    ws.onclose = function(evt) {\n" +
            "      console.log(\"Connection closed.\");\n" +
            "    };\n" +
            "</script>\n" +
            "</body>\n" +
            "</html>";

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        System.out.println("----" + msg.uri());
        if (msg.uri().equals("/ws")) {
            // 是ws升级协议
            ctx.fireChannelRead(msg.retain());
            System.out.println("---1111");
        } else {
            // 普通http请求
            DefaultFullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
            String out = MSG;
            byte[] data = out.getBytes();
            httpResponse.headers().set(HttpHeaders.Names.CONTENT_LENGTH, data.length);
            httpResponse.content().writeBytes(data);
            ctx.writeAndFlush(httpResponse);
            System.out.println("22222");
        }
    }
}

class MyWSFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) throws Exception {
        if (msg instanceof TextWebSocketFrame) {
            System.out.println(msg);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt == WebSocketServerProtocolHandler
                .ServerHandshakeStateEvent.HANDSHAKE_COMPLETE) {
            ctx.pipeline().remove(MyHttpRequestHandler.class);
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
