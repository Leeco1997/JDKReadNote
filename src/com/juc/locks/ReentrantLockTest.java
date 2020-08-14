package com.juc.locks;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author liqiao
 * @date 2020/4/9 21:00
 * @description 可重入锁  共享锁/独占锁
 * <p>
 * 1. tryLock()
 * 2. lock.
 */
public class ReentrantLockTest {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock(false);
        lock.lock();
        Thread t1 = new Thread(() -> {
            try {
                lock.lockInterruptibly();
                System.out.println("t1 start");
                TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
            } catch (InterruptedException e) {
                System.out.println("t1 end 。");
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                //被打断以后，会执行当前线程
                lock.lockInterruptibly();
                System.out.println("t2 start");
            } catch (InterruptedException e) {
                System.out.println("interrupt");
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        t1.start();
        //t2.start();
        Thread.sleep(10);
        t1.interrupt();
        Thread.interrupted();
        Thread.currentThread().isInterrupted();
        //中断标志会被清楚
        System.out.println(t1.isInterrupted());

    }


    public void test() throws InterruptedException {
        // 1.初始化选择公平锁、非公平锁
        ReentrantLock lock = new ReentrantLock(true);
        // 2.可用于代码块
        lock.lock();
        try {
            try {
                // 3.支持多种加锁方式，比较灵活; 具有可重入特性
                if (lock.tryLock(100, TimeUnit.MILLISECONDS)) {
                }
            } finally {
                // 4.手动释放锁
                lock.unlock();
            }
        } finally {
            lock.unlock();
        }
    }


}
 class ReadWriteLockTest {
    public static void main(String[] args) {
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    }
}
