package juc.thread;


import org.junit.Test;

import java.util.concurrent.*;

/**
 * @author liqiao
 * @date 2020/4/9 20:58
 * 线程池基本使用：
 * # ExecutorService下的两种常用线程池：
 * 1.ThreadPoolExecutor
 * 2.ScheduledThreadPoolExecutor
 *
 * # Executors的三种线程池：
 * FixedThreadPool
 * SingleThreadExecutor
 * CachedThreadPool
 *
 */
public class ThreadPoolTest {
    private static final int CORE_POOL_SIZE = 5;
    private static final int MAXIMUM_POOL_SIZE = 10;
    private static final long KEEP_ALIVE_TIME = 5L;
    private static final int QUEUE_CAPACITY = 100;

    /**
     * ThreadPoolExecutor使用样例
     */
    @Test
    private void ThreadPoolExecutoTest(){
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
        //结束线程池 todo:一定要手动关闭线程池吗？
        threadPoolExecutor.shutdown();
        while (!threadPoolExecutor.isTerminated()) {
        }
        System.out.println("已关闭线程池");
    }

    private void fixedThreadPoolTest(){
        ExecutorService executor= Executors.newFixedThreadPool(5);

    }

}
