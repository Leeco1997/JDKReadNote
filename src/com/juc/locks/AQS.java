package com.juc.locks;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

/**
 * @author liqiao
 * @date 2020/7/16 11:21
 * @description CAS && synchronized
 * cas 乐观锁---一直自选，消耗cpu
 * synchronized 乐观锁 ---wait的时候不消耗资源
 * <p>
 * <p>
 * AQS 原理: state + CLH（双向链表）
 * 实现的业务逻辑：
 * 1. 什么时候加入到队列？
 * 2. 什么时候能获取到锁？
 */
@Slf4j
public class AQS {
    public static void main(String[] args) {


        ReentrantLock lock = new ReentrantLock();

        Thread t1 = new Thread(() -> {
            try {
                log.debug("启动...");
                lock.lockInterruptibly();
                while (true){
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.debug("等锁的过程中被打断");
                return;
            } finally {
                lock.unlock();
            }
        }, "t1");


       // lock.lock();
       // log.debug("main获得了锁");
        t1.start();
        try {
            TimeUnit.SECONDS.sleep(1);
            t1.interrupt();
            log.debug("执行打断  " +"t1 = "+ t1.getState() +" " + t1.isInterrupted());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
           // lock.unlock();
        }
    }
}
