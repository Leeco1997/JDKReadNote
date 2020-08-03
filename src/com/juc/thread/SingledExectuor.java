package com.juc.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author liqiao
 * @date 2020/7/27 22:12
 * @description
 */

public class SingledExectuor {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        for (int i = 0; i < 10; i++) {
            executorService.execute(()->{
                System.out.println("a");
                System.out.println("b");
                System.out.println("c");
            });
        }
    }
}
