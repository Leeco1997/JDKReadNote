package com.juc.thread;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author liqiao
 * @date 2020/6/23 20:57
 * @description 三个线程顺序打印ABC
 * 1. lock + state
 * 2. lock + Condition
 * 3. Semaphore
 * 信号量内部维护了一个计数器，其值为可以访问的共享资源的个数。
 * 一个线程要访问共享资源，先获得信号量，如果信号量的计数器值大于1，意味着有共享资源可以访问，则使其计数器值减去1，再访问共享资源。
 * 4. AtomicInteger
 */

public class MulitThread {
    public static void main(String[] args) {
        MulitThread test = new MulitThread();
//        test.thread1.start();
//        test.thread2.start();
//        test.thread3.start();


//        test.t1.start();
//        test.t2.start();
//        test.t3.start();

//        test.s1.start();
//        test.s2.start();
//        test.s3.start();

        test.a1.start();
        test.a2.start();
        test.a3.start();

    }

    //可重入锁
    public synchronized void test() {
        System.out.println("do something.");
        synchronized (this) {
            System.out.println("……");
        }
    }

    //方法一
    static Lock lock = new ReentrantLock();
    static int state = 1;
    Thread thread1 = new Thread(() -> {
        for (int i = 0; i < 10; ) {
            try {
                lock.lock();
                while (state % 3 == 1) {
                    System.out.println("第" + i + "次输出A");
                    state++;
                    i++;
                }
            } finally {
                lock.unlock();
            }
        }
    });

    Thread thread2 = new Thread(() -> {
        for (int i = 0; i < 10; ) {
            try {
                lock.lock();
                while (state % 3 == 2) {
                    System.out.println("第" + i + "次输出B");
                    state++;
                    i++;
                }
            } finally {
                lock.unlock();
            }
        }
    });

    Thread thread3 = new Thread(() -> {
        for (int i = 0; i < 10; ) {
            try {
                lock.lock();
                while (state % 3 == 0) {
                    System.out.println("第" + i + "次输出C");
                    state++;
                    i++;
                }
            } finally {
                lock.unlock();
            }
        }
    });

    //方法二
    static Lock lock2 = new ReentrantLock();
    static int count = 0;
    static Condition a = lock2.newCondition();
    static Condition b = lock2.newCondition();
    static Condition c = lock2.newCondition();

    Thread t1 = new Thread(() -> {
        for (int i = 0; i < 10; i++) {
            try {
                lock2.lock();
                while (count % 3 != 0) {
                    a.await();
                }
                System.out.println("第" + i + "次输出A");
                count++;
                b.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock2.unlock();
            }
        }
    });

    Thread t2 = new Thread(() -> {
        for (int i = 0; i < 10; i++) {
            try {
                lock2.lock();
                while (count % 3 != 1) {
                    b.await();
                }
                System.out.println("第" + i + "次输出B");
                count++;
                c.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock2.unlock();
            }
        }
    });

    Thread t3 = new Thread(() -> {
        for (int i = 0; i < 10; i++) {
            try {
                lock2.lock();
                while (count % 3 != 2) {
                    c.await();
                }
                System.out.println("第" + i + "次输出C");
                count++;
                a.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock2.unlock();
            }
        }
    });

    //第三种 信号量
    //acquire() 计数器减一
    //release() 计数器+1
    static Semaphore sa = new Semaphore(1);
    static Semaphore sb = new Semaphore(0);
    static Semaphore sc = new Semaphore(0);

    Thread s1 = new Thread(() -> {
        for (int i = 0; i < 10; i++) {
            try {
                sa.acquire();
                System.out.println("第" + i + "次输出A");
                sb.release();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //sa.release();
        }
    });
    Thread s2 = new Thread(() -> {
        for (int i = 0; i < 10; i++) {
            try {
                sb.acquire();
                System.out.println("第" + i + "次输出B");
                sc.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //sa.release();
        }
    });
    Thread s3 = new Thread(() -> {
        for (int i = 0; i < 10; i++) {
            try {
                sc.acquire();
                System.out.println("第" + i + "次输出C");
                System.out.println("============");
                sa.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });

    //第四种
    static AtomicInteger atomicInteger = new AtomicInteger(0);
    static int maxNum = 30;
    Thread a1 = new Thread(() -> {
        for (int i = 0; i < 10; i++) {
            while (atomicInteger.intValue() < maxNum) {
                if (atomicInteger.intValue() % 3 == 0 && atomicInteger.intValue() < maxNum) {
                    System.out.println("第" + atomicInteger + "次输出A");
                    atomicInteger.getAndIncrement();
                }
            }
        }
    });
    Thread a2 = new Thread(() -> {
        for (int i = 0; i < 10; i++) {
            while (atomicInteger.intValue() < maxNum) {
                if (atomicInteger.intValue() % 3 == 1) {
                    System.out.println("第" + atomicInteger + "次输出B");
                    atomicInteger.getAndIncrement();
                }
            }
        }
    });
    Thread a3 = new Thread(() -> {
        for (int i = 0; i < 10; i++) {
            while (atomicInteger.intValue() < maxNum) {
                if (atomicInteger.intValue() % 3 == 2) {
                    System.out.println("第" + atomicInteger + "次输出C");
                    atomicInteger.getAndIncrement();
                }
            }
        }
    });


}

