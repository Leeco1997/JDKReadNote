package juc.locks;

/**
 * @author liqiao
 * @date 2020/6/5 17:32
 * @description 这是一个死锁代码样例
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
                    System.out.println(Thread.currentThread().getName() +" I has got lockA");
                    synchronized (lockB) {
                        System.out.println(Thread.currentThread().getName() +" I has got lockB");

                    }
                }
            }
        };
        Thread thread2 = new Thread() {
            @Override
            public void run() {
                synchronized (lockB) {
                    System.out.println(Thread.currentThread().getName() + " I has got lockB");
                    try {
                        Thread.currentThread().sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (lockA) {
                        System.out.println(Thread.currentThread().getName()+" I has got lockA");

                    }
                }
            }
        };
        thread1.start();
        thread2.start();

    }
}
