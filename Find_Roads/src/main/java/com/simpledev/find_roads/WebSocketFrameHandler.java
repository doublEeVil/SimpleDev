package com.simpledev.find_roads;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class WebSocketFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    private static AtomicInteger inc = new AtomicInteger(33);
    private static Map<Channel, Integer> channelIdMap = new ConcurrentHashMap<>();
    private static Map<Integer, int[]> posMap = new ConcurrentHashMap<>();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        // ping and pong frames already handled

        if (frame instanceof TextWebSocketFrame) {
            // Send the uppercase string back.
            String request = ((TextWebSocketFrame) frame).text();
            ctx.channel().writeAndFlush(new TextWebSocketFrame(request.toUpperCase(Locale.US)));
        } else if (frame instanceof BinaryWebSocketFrame) {
            BinaryWebSocketFrame webSocketFrame = (BinaryWebSocketFrame)frame;
            ByteBuf buf =webSocketFrame.content();
            buf.markReaderIndex();

            int id = channelIdMap.get(ctx.channel());
            int x = buf.readInt();
            int y = buf.readInt();
            posMap.putIfAbsent(id, new int[]{x, y});
            int[] pos = posMap.get(id);
            pos[0] = x;
            pos[1] = y;

            onMove(id, x, y);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        int id = inc.incrementAndGet();
        channelIdMap.put(ctx.channel(), id);
        onAdd(id, ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        int id = channelIdMap.remove(ctx.channel());
        onRemove(id);
    }

    private static ScheduledExecutorService pool = Executors.newScheduledThreadPool(4);

    public void onAdd(int id, Channel channel) {
        ByteBuf buf = Unpooled.directBuffer();
        buf.markWriterIndex();
        buf.writeInt(id);
        buf.writeByte(0);
        buf.writeInt(0);
        buf.writeInt(0);
        // channel.writeAndFlush(buf);
        pool.schedule(() -> {
                channel.writeAndFlush(new BinaryWebSocketFrame(buf));
                broadcastAllPos(channel);
            }, 200L, TimeUnit.MILLISECONDS);
    }

    public void onRemove(int id) {
        ByteBuf buf = Unpooled.directBuffer();
        buf.markWriterIndex();
        buf.writeInt(id);
        buf.writeByte(2);
        buf.writeInt(0);
        buf.writeInt(0);
        broadcast(buf);
    }

    public void onMove(int id, int x, int y) {
        System.out.println("---send: id:" + id + " x:" + x + " y:" + y);
        ByteBuf buf = Unpooled.directBuffer();
        buf.markWriterIndex();
        buf.writeInt(id);
        buf.writeByte(1);
        buf.writeInt(x);
        buf.writeInt(y);
        broadcast(buf);
    }

    private void broadcast(ByteBuf buf) {
        channelIdMap.keySet().forEach(ch -> {
            ch.writeAndFlush(new BinaryWebSocketFrame(buf.copy()));
            //buf.retain();
        });
    }

    private void broadcastAllPos(Channel channel) {
        posMap.entrySet().forEach(entry -> {
            ByteBuf buf = Unpooled.directBuffer();
            buf.markWriterIndex();
            buf.writeInt(entry.getKey());
            buf.writeByte(1);
            buf.writeInt(entry.getValue()[0]);
            buf.writeInt(entry.getValue()[1]);
            channel.writeAndFlush(new BinaryWebSocketFrame(buf));
        });
    }
}
