package com.io.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @author liqiao
 * @date 2020/8/3 21:46
 * @description
 */

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 8888);
        socket.getOutputStream().write("hello".getBytes());
        socket.getOutputStream().flush();

        System.out.println("waiting for message back ……");
        InputStream inputStream = socket.getInputStream();
        int read = inputStream.read(new byte[1024]);
        System.out.println(new String(new byte[1024], 0, read));

    }
}
