package com.simpledev.netty_batch_request.http;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * 实际的行为
 */
public class HttpAction {
    private Dispatcher dispatcher;

    private String host;
    private int port;
    private String path;
    private SocketChannel socketChannel;
    ByteBuffer buffer;

    public HttpAction(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
        buffer = ByteBuffer.allocateDirect(64);
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    public boolean isActive() {
        return socketChannel.isConnected();
    }

    /**
     * 处理发送请求
     * @param url
     */
    public void sendHttpRequest(String url) throws IOException {
        solveUrl(url);

        SocketChannel client = SocketChannel.open(new InetSocketAddress(host, port));
        this.socketChannel = client;
        client.configureBlocking(false);
        dispatcher.register(this, client);

        String s = "GET " + path + " HTTP/1.1\r\n" +
                "Host: " + host + ":" + port + "\r\n" +
                "Connection: keep-alive\r\n" +
                "Cache-Control: max-age=0\r\n" +
                "Upgrade-Insecure-Requests: 1\r\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.135 Safari/537.36\r\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\r\n" +
                "\r\n";   // 很重要，必须要有这个，不然服务端无法解析这个请求

        client.write(ByteBuffer.wrap(s.getBytes()));
    }

    private void solveUrl(String url) {
        // 只处理http
        // http://127.0.0.1/call
        // http://baidu.com/call

        url = url.replace("http://", "");
        String[] urlParts = url.split("/");
        String u1 = urlParts[0];
        if (u1.contains(":")) {
            String[] hostPort = u1.split(":");
            host = hostPort[0];
            port = Integer.valueOf(hostPort[1]);
        } else {
            host = u1;
            port = 80;
        }
        path = url.replace(u1, "");
    }

    /**
     * 接受服务端返回的消息
     * 这里的复用有点问题，没有clear掉
     */
    public void onReceiveServerMsg() throws IOException {
        socketChannel.read(buffer);
        buffer.clear();
//        buffer.flip();
//        if (buffer.limit() == 0) return;
//        byte[] data = new byte[buffer.limit()];
//        buffer.get(data);
//        String s = new String(data);
//        System.out.println("----收到服务器消息：" + s);
    }
}
