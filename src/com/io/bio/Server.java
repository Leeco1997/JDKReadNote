package com.io.bio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * @author liqiao
 * @date 2020/8/3 21:46
 * @description
 */

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress("127.0.0.1", 8888));
        while (true) {
            Socket accept = serverSocket.accept();
            new Thread(() -> {
                try {
                    handler(accept);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }

    }

    private static void handler(Socket accept) throws IOException {
        byte[] bytes = new byte[1024];
        int read = accept.getInputStream().read(bytes);
        System.out.println(new String(bytes, 0, read));

        accept.getOutputStream().write(bytes, 0, read);
        accept.getOutputStream().flush();


    }
}
