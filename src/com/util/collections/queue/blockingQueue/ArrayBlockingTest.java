package com.util.collections.queue.blockingQueue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Stack;
import java.util.concurrent.*;

/**
 * @author liqiao
 * @date 2020/7/8 12:46
 * @description
 */

public class ArrayBlockingTest {
    static BlockingQueue<Integer> blockingQueue = new LinkedBlockingDeque<Integer>(10);

    public static void main(String[] args) {
//        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 2, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));
//        threadPoolExecutor.execute(new Producer());
//        threadPoolExecutor.execute(new Consumer());
        new Thread(new Producer()).start();
        new Thread(new Producer()).start();
        new Thread(new Consumer()).start();
    }

    static class Producer implements Runnable {

        @Override
        public void run() {
            for (int i = 1; i < 10; i++) {
                   try {
                       blockingQueue.put(i);
                       System.out.println("生产后余额："+blockingQueue.size());
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
            }
        }
    }

     static class Consumer implements Runnable {

        @Override
        public void run() {
            for (int i = 1; i < 10; i++) {
                try {
                    blockingQueue.take();
                    System.out.println("消费后余额："+blockingQueue.size());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
