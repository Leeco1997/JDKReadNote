package com.juc.locks;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author liqiao
 * @date 2020/7/23 21:38
 * @description cyclicBarrier计数器可以重置，countDownLatch不能
 * 只有等所有线程抵达栅栏的时候，才会继续执行
 */
@Slf4j
public class CyclicBarrierTest {
    public static void main(String[] args) {
        //可以指定线程到达屏障后，先执行什么
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> {
            log.info("main");
        });

        cyclicBarrier.isBroken();
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 6; i++) {
            Runnable runnable = () -> {
                log.info("{} has arrived", Thread.currentThread().getName());
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                log.info("{} begin ...", Thread.currentThread().getName());
            };
            executorService.execute(runnable);
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();

    }
}
