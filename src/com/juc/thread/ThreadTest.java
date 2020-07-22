package com.juc.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * @author liqiao
 * @date 2020/7/7 20:16
 * @description 1. 创建线程的方式
 * 2. sleep() wait() interrupt()
 * 3. 实现一个监控线程
 * 4. 烧开水泡茶  清洗茶壶->清洗茶叶->烧开水->paocha
 */
@Slf4j(topic = "thread")
public class ThreadTest {

    public static void main(String[] args) {

        //写一个监控线程
        Thread t = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    log.info("thread is interrupt {}", Thread.currentThread().isInterrupted());
                }
                try {
                    Thread.sleep(2000);
                    log.info("monitor");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    //重新设置打断标志
                    Thread.currentThread().interrupt();
                }

            }
        });

        Thread t1 = new Thread(() -> {
            try {
                log.info("清洗茶壶");
                Thread.sleep(500);
                log.info("烧水");
                Thread.sleep(2000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        Thread t2 = new Thread(() -> {
            try {
                log.info("洗茶叶");
                Thread.sleep(500);
                t1.join();
                log.info("泡茶");
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();

    }

    /**
     * 1. new Thread()
     * 2. runnable方法可以使用匿名内部类，也可以重写一个类。
     * 3. 使用lambda表达式
     */
    @Test
    public void nreThread() throws ExecutionException, InterruptedException {
        //第一种
        Runnable runnable = () -> {
            log.info("running1");
        };
        new Thread(runnable, "t1").start();

        //第二种
        new Thread("t2") {
            @Override
            public void run() {
                log.info("running2");
            }
        }.start();

        //使用Future
        FutureTask futureTask = new FutureTask<>(() -> {
            log.info("t3 begin");
            Thread.sleep(1000);
            log.info("t3 end");
            return 100;
        });
        Thread thread = new Thread(futureTask, "t3");
        thread.start();
        //阻塞获取
        log.info(String.valueOf(futureTask.get()));
        log.info("end ");
    }

    @Test
    /*
     * sleep()的时候，调用interrupt(),会清楚标记位
     */
    public void sleepTest() {
        Thread t4 = new Thread(() -> {
            log.info("t4 running");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t4");
        t4.start();
        try {
            Thread.sleep(500);
            t4.interrupt();
            t4.join();
            log.info("{}", t4.getState());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    /*
     * sleep()的时候，调用interrupt(),会清楚标记位
     */
    public void waitTest() {
        Thread t4 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    log.info("t4 running {}", Thread.currentThread().isInterrupted());
                    break;
                }
            }

        }, "t4");
        t4.start();
        try {
            Thread.sleep(100);
            log.info("interrupt begin ");
            t4.interrupt();
            log.info("{},{}", t4.getState(), t4.isInterrupted());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void parkTest() {
        Thread t1 = new Thread(() -> {
            LockSupport.park();
            log.info("parked");
            //正常状态下的park  RUNNABLE,true
            log.info("{},{}", Thread.currentThread().getState(), Thread.currentThread().isInterrupted());

            //interrupted()为true的时候，park()会失效
            LockSupport.park();
            log.info("park again");
        });
        t1.start();

        t1.interrupt();
    }

    /**
     * 洗茶壶烧水泡茶叶
     */
    @Test
    public void dosomething() {

    }

}
