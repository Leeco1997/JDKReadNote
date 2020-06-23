package juc.thread;


import sun.nio.ch.ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author liqiao
 * @date 2020/4/9 20:58
 */
public class ThreadPoolTest {
    public static void main(String[] args) {
        Executors.newCachedThreadPool();

    }


}
