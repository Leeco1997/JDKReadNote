package com.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author liqiao
 * @date 2020/8/3 22:05
 * @description nio是双向通道，可以读写
 * 单线程的Nio
 * 缺点：select如果阻塞，则比较麻烦
 */

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress("127.0.0.1", 8888));
        System.out.println("server has started.");
        Selector selector = Selector.open();
        //选择监听的事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            //select是阻塞的方法
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey next = iterator.next();
                //避免重复处理
                iterator.remove();
                handle(next);
            }
        }
    }

    public static void handle(SelectionKey selectionKey) throws IOException {
        if (selectionKey.isAcceptable()) {
            ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
            try (SocketChannel accept = channel.accept()) {
                accept.configureBlocking(false);
                //再监听读事件
                accept.register(selectionKey.selector(), SelectionKey.OP_READ);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (selectionKey.isReadable()) {
            SocketChannel channel = (SocketChannel) selectionKey.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(512);
            byteBuffer.clear();
            try {
                int read = channel.read(byteBuffer);
                if (read != -1) {
                    System.out.println(new String(byteBuffer.array(), 0, read));
                }
                //回复
                ByteBuffer wrap = ByteBuffer.wrap("HelloClient".getBytes());
                channel.write(wrap);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (channel != null) {
                    channel.close();
                }
            }

        }
    }
}
