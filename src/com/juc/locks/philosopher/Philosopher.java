package com.juc.locks.philosopher;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author liqiao
 * @date 2020/7/21 15:08
 * @description 哲学家就餐问题
 */

public class Philosopher extends Thread {
    Chopstick left;
    Chopstick right;

    Philosopher(String name, Chopstick left, Chopstick right) {
        super(name);
        this.left = left;
        this.right = right;
    }

    @Override
    public void run() {
        while (true) {
            //使用tryLock
            if (right.tryLock()) {
                if (left.tryLock()) {
                    eat();
                    left.unlock();
                }
                right.unlock();
            }
        }
    }

    private void eat() {
        try {
            System.out.println(Thread.currentThread().getName() + " is eat……");
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

class Chopstick extends ReentrantLock {
    private String name;

    Chopstick(String name) {
        this.name = name;
    }

}