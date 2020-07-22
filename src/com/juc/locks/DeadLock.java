package com.juc.locks;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author liqiao
 * @date 2020/6/5 17:32
 * @description 死锁/活锁/饥饿    死锁和饥饿可以使用reentrantLock
 * 如何检测死锁？
 * jps 查看进程id
 * jstack id 查看栈信息
 * jconsole 检测死锁
 */

public class DeadLock {
    private static String lockA = "A";
    private static String lockB = "B";

    public static void main(String[] args) {
        new DeadLock().deadLockTest();
    }

    private void deadLockTest() {
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                synchronized (lockA) {
                    try {
                        System.out.println(Thread.currentThread().getName() + " I has got lockA");
                        TimeUnit.SECONDS.sleep(1);
                        synchronized (lockB) {
                            System.out.println(Thread.currentThread().getName() + " I has got lockB");

                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread thread2 = new Thread() {
            @Override
            public void run() {
                synchronized (lockB) {
                    try {
                        System.out.println(Thread.currentThread().getName() + " I has got lockB");
                        TimeUnit.SECONDS.sleep(2);
                        synchronized (lockA) {
                            System.out.println(Thread.currentThread().getName() + " I has got lockA");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread1.start();
        thread2.start();

    }
}

/**
 * 活锁：改变对方的结束条件，让对方无法结束
 * 解决：增加一定的随机时间。
 */
@Slf4j
class LiveLock {
    static int count = 10;

    public static void main(String[] args) {

        new Thread(() -> {
            while (true) {
                if (count != 0) {
                    count--;
                }
            }
         //   System.out.println("end");
        }).start();

        new Thread(() -> {
            while (true) {
                if (count != 0) {
                    count++;
                }
            }
           // System.out.println("end");
        }).start();
    }
}
