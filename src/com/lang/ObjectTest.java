package com.lang;

import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

/**
 * @author liqiao
 * @date 2020/4/25
 * @description 关于Object类中几个方法。
 * Object o = new Object();产生了几个对象？占用多少字节？
 */

public class ObjectTest {
    public static void main(String[] args) {
        /*
         * 输出结果:
         * Thread-0 is waiting for lock
         * Thread-0 gets the lock
         * Thread-1 is waiting for lock
         * Thread-0 is doing something
         * Thread-1 gets the lock
         * Thread-1 is doing something
         * Thread-0 finish
         * ObjectMonitor对象中有两个队列：_WaitSet 和 _EntryList,分别存放wait状态和等待锁状态的线程。
         */
        Object object = new Object();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " is waiting for lock");
            synchronized (object) {
                try {
                    System.out.println(Thread.currentThread().getName() + " gets the lock");
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println(Thread.currentThread().getName() + " is doing something");
                    object.wait(100);
                    System.out.println(Thread.currentThread().getName() + " finish");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " is waiting for lock");
            synchronized (object) {
                try {
                    System.out.println(Thread.currentThread().getName() + " gets the lock");
                    object.notify();
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println(Thread.currentThread().getName() + " is doing something");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            System.out.println("finalize success");
        } finally {
            super.finalize();
        }
    }

    @Test
    public void testWaitNotify() throws InterruptedException {
        Object obj = new Object();
        //java.lang.IllegalMonitorStateException:current thread not owner
        obj.wait();
        obj.notify();
    }

    @Test
    public void test() {
        Object o = new Object();
        // OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
        //      0     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
        //      4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
        //      8     4        (object header)                           e5 01 00 20 (11100101 00000001 00000000 00100000) (536871397)
        //     12     4        (loss due to the next object alignment) [对齐为8的整数]
        //Instance size: 16 bytes
        //Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
        //markword 8字节 class pointer 4字节（默认开启压缩）  padding4字节
        //如果不开启压缩  class pointer 8字节你
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
        synchronized (o) {
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }

    }

    @Test
    public void test0(){
            int i = 0;
            for (int j = 0; j < 50; j++) {
                i = i++;
            }
            System.out.println(i);
        }


}
