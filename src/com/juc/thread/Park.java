package com.juc.thread;

import java.util.concurrent.locks.LockSupport;

/**
 * @author liqiao
 * @date 2020/7/21 11:57
 * @description 交替输出
 */

public class Park {
    static Thread t1;
    static Thread t2;
    static Thread t3;

    public static void main(String[] args) {
        Park up = new Park();
        t1 = new Thread(() -> {
            up.print("a", t2);
        });
        t2 = new Thread(() -> {
            up.print("b", t3);
        });
        t3 = new Thread(() -> {
            up.print("c", t1);
        });
        t1.start();
        t2.start();
        t3.start();
        LockSupport.unpark(t1);
    }

    private void print(String str, Thread next) {
        for (int i = 0; i < 10; i++) {
            LockSupport.park();
            System.out.println(str);
            LockSupport.unpark(next);
        }
    }


}
