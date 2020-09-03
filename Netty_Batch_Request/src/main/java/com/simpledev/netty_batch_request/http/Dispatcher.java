package com.simpledev.netty_batch_request.http;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;

/**
 * 处理数据的发送和接受
 */
public class Dispatcher {
    private int threadNum;

    private Selector[] selector;
    private int index = 0;

    public Dispatcher(int threadNum) throws IOException {
        this.threadNum = threadNum;
        onInit();
    }

    private void onInit() throws IOException {
        selector = new Selector[threadNum];
        for (int i = 0; i < threadNum; i++) {
            //selector[i] = SelectorProvider.provider().openSelector();
            selector[i] = Selector.open();
            new Thread(new DispatchThread(selector[i])).start();
        }
    }

    public void register(HttpAction action, SocketChannel socketChannel) throws IOException {
        int _d = index++ % threadNum;
        socketChannel.register( selector[_d], SelectionKey.OP_CONNECT, action);
        socketChannel.register( selector[_d], SelectionKey.OP_READ, action);
        //socketChannel.register( selector[_d], SelectionKey.OP_WRITE, action);
    }

    private void onConnect(SelectionKey key) throws IOException {
        HttpAction action = (HttpAction) key.attachment();
        action.getSocketChannel().finishConnect();
    }

    private void onWriteToServer(SelectionKey key) throws IOException {
        // SocketChannel socketChannel = (SocketChannel) key.attachment();

    }

    private void onReceiveFromServer(SelectionKey key) throws IOException {
        HttpAction action = (HttpAction) key.attachment();
        action.onReceiveServerMsg();
    }

    class DispatchThread implements Runnable {
        Selector selector;

        public DispatchThread(Selector selector) {
            this.selector = selector;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    int count = selector.selectNow();
                    if (count == 0) {
                        continue;
                    }
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = keys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        if (key.isConnectable()) {
                            onConnect(key);
                        } else if (key.isWritable()) {
                            onWriteToServer(key);
                        } else if (key.isReadable()) {
                            onReceiveFromServer(key);
                        }
                        iterator.remove();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
