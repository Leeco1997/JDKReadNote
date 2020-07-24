package com.juc.locks;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author liqiao
 * @date 2020/7/20 22:46
 * @description latch设计模式用于等待所有的子任务完成任务
 */
@Slf4j
public class CountDownLatchTest {
    public static void main(String[] args) {
        final CountDownLatch countDownLatch = new CountDownLatch(5);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 3, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2));
        for (int i = 0; i < 5; i++) {
            Runnable runnable = () -> {
                log.info("{} is running .", Thread.currentThread().getName());
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            };
            threadPoolExecutor.execute(runnable);
        }
        log.info("{} is waiting ", Thread.currentThread().getName());
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("子任务运行完毕");

    }


}
