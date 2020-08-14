package com.io.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author liqiao
 * @date 2020/8/3 21:45
 * @description 异步io, 不需要轮询;使用钩子选择下一步要执行的代码
 * aio vs nio
 * 在linux底层都是 `epoll`; windows的 aio是基于event
 * netty的底层是nio,
 */

public class Server {
    public static void main(String[] args) throws IOException {
        //单线程
        final AsynchronousServerSocketChannel serverSocketChannel = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(8888));

        //多线程
        ExecutorService executorService = Executors.newCachedThreadPool();
        AsynchronousChannelGroup asynchronousChannelGroup = AsynchronousChannelGroup.withCachedThreadPool(executorService, 1);
        AsynchronousServerSocketChannel bind = AsynchronousServerSocketChannel.open(asynchronousChannelGroup).bind(new InetSocketAddress(8888));

        //accept是非阻塞的
        serverSocketChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
            @Override
            public void completed(AsynchronousSocketChannel client, Object attachment) {
                //释放这次连接
                serverSocketChannel.accept(null,this);
                try {
                    System.out.println(client.getRemoteAddress());
                    ByteBuffer allocate = ByteBuffer.allocate(1024);
                    //钩子
                    client.read(allocate, allocate, new CompletionHandler<Integer, ByteBuffer>() {
                        @Override
                        public void completed(Integer result, ByteBuffer attachment) {
                           //每次读写之前都需要调用flip,重置position
                            attachment.flip();
                            System.out.println(new String(attachment.array(), 0, result));
                            client.write(ByteBuffer.wrap("helloClient".getBytes()));
                        }

                        @Override
                        public void failed(Throwable exc, ByteBuffer attachment) {
                            exc.printStackTrace();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                exc.printStackTrace();

            }
        });

        //防止Main线程结束
        while (true){
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
