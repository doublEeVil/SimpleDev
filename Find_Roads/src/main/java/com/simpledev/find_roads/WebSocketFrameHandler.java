package com.simpledev.find_roads;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

import java.nio.ByteBuffer;
import java.util.Locale;

public class WebSocketFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        // ping and pong frames already handled

        // System.out.println("---rcv: " + frame.getClass().getName());
        if (frame instanceof TextWebSocketFrame) {
            // Send the uppercase string back.
            String request = ((TextWebSocketFrame) frame).text();
            ctx.channel().writeAndFlush(new TextWebSocketFrame(request.toUpperCase(Locale.US)));
        } else if (frame instanceof BinaryWebSocketFrame) {
            BinaryWebSocketFrame webSocketFrame = (BinaryWebSocketFrame)frame;
            ByteBuf buf =webSocketFrame.content();
            buf.markReaderIndex();
            buf.readInt();
            buf.readInt();
            System.out.println("x: " + buf.readInt() + " y:" + buf.readInt());
        }
    }
}
