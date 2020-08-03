package com.juc.thread.threadpool;


import com.juc.thread.MyRunnable;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liqiao
 * @date 2020/4/9 20:58
 * 线程池基本使用：
 * # ExecutorService下的两种常用线程池：
 * 1.ThreadPoolExecutor
 * 2.ScheduledThreadPoolExecutor
 * <p>
 * # Executors的三种线程池：
 * FixedThreadPool
 * SingleThreadExecutor
 * CachedThreadPool
 */
@Slf4j
public class ThreadPoolTest {
    private static final int CORE_POOL_SIZE = 5;
    private static final int MAXIMUM_POOL_SIZE = 10;
    private static final long KEEP_ALIVE_TIME = 5L;
    private static final int QUEUE_CAPACITY = 100;

    public static void main(String[] args) {
        MyThreadPool myThreadPool = new MyThreadPool(3, 10, 1, 5, ((queue, task) -> {
            log.info("放弃{}", task);
        }));
        for (int i = 0; i < 10; i++) {
            int j = i;
            myThreadPool.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("{}", j);
            });
        }
    }

    /**
     * ThreadPoolExecutor使用样例
     */
    @Test
    public void threadPoolExecutoTest() {

        //推荐使用ThreadPoolExecutor
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAXIMUM_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(QUEUE_CAPACITY),
                new ThreadPoolExecutor.CallerRunsPolicy());
        //有20个任务
        for (int i = 0; i < 20; i++) {
            MyRunnable myRunnable = new MyRunnable(i);
            threadPoolExecutor.execute(myRunnable);
        }
        threadPoolExecutor.shutdown();
        while (!threadPoolExecutor.isTerminated()) {
        }
        System.out.println("已关闭线程池");
    }

    @Test
    public void fixedThreadPoolTest() {
        //默认的线程工厂  namePrefix = "pool-" + poolNumber.getAndIncrement() + "-thread-";
        ExecutorService executor = Executors.newFixedThreadPool(3, new ThreadFactory() {
            AtomicInteger atomicInteger = new AtomicInteger(1);
            @Override
            public Thread newThread(Runnable r) {
                MyRunnable myRunnable = new MyRunnable(atomicInteger.get());
                return new Thread(myRunnable, "线程" + atomicInteger.getAndIncrement());
            }
        });
        executor.execute(()->{
            log.info("0");
        });

        executor.execute(()->{
            log.info("1");
        });
        executor.execute(()->{
            log.info("3");
        });

    }

}
