package com.juc.thread.threadpoll;

import javafx.concurrent.Worker;
import lombok.extern.slf4j.Slf4j;
import org.junit.internal.runners.statements.RunAfters;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author liqiao
 * @date 2020/7/21 17:19
 * @description 自定义实现一个线程池
 * 1. 自定义拒绝策略
 * 2. 自定义实现阻塞队列 【ArrayDeque + ReentrantLock + Condition】
 * 3. 自定义线程池
 */
@Slf4j
public class MyThreadPool {

    private MyBlockingQueue<Runnable> taskQueue;
    /**
     * 线程集合
     */
    private HashSet<Worker> workers = new HashSet<>();
    private RejectPolicy<Runnable> rejectPolicy;
    private int coreSize;
    private int maxSize;
    private long timeout;

    public MyThreadPool(int coreSize, int maxSize, long timeout, int queueSize,RejectPolicy<Runnable> rejectPolicy) {
        this.coreSize = coreSize;
        this.maxSize = maxSize;
        this.timeout = timeout;
        this.taskQueue = new MyBlockingQueue<>(queueSize);
        this.rejectPolicy = rejectPolicy;
    }

    public void execute(Runnable task) {
        synchronized (workers) {
            if (workers.size() < coreSize) {
                Worker worker = new Worker(task);
                log.info("新增worker {} ", task);
                workers.add(worker);
                worker.start();
            } else {
                //队列满了怎么办？  死等/超时等待/抛出异常/执行拒绝策略

                //taskQueue.put(task);
                //taskQueue.offer(1, TimeUnit.SECONDS, task);
                taskQueue.tryPut(rejectPolicy,task);
            }
        }
    }

    private class Worker extends Thread {
        private Runnable task;

        Worker(Runnable task) {
            this.task = task;
        }

        @Override
        public void run() {
            //task或者任务队列不为空,超时的时候会返回null
            while (task != null || (task = taskQueue.poll(timeout, TimeUnit.SECONDS)) != null) {
                try {
                    task.run();
                    log.info("{} is running", task);
                } finally {
                    task = null;
                }
            }
            synchronized (workers) {
                workers.remove(this);
                log.info("worker线程 has removed");
            }
        }
    }


}

/**
 * 阻塞队列
 *
 * @param <T>
 */
@Slf4j
class MyBlockingQueue<T> {
    /**
     * FIFO的队列
     * 获取元素的时候需要用锁
     * 容量
     */

    private Deque<T> deque = new ArrayDeque<>();

    private ReentrantLock lock = new ReentrantLock();
    private Condition fullWait = lock.newCondition();
    private Condition emptyWait = lock.newCondition();

    private int capcity;

    public MyBlockingQueue(int capcity) {
        this.capcity = capcity;
    }

    /**
     * 阻塞获取元素
     *
     * @return
     */
    public T take() {
        lock.lock();
        try {
            while (deque.isEmpty()) {
                try {
                    emptyWait.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t = deque.removeFirst();
            fullWait.signal();
            return t;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 阻塞添加
     *
     * @return
     */
    public void put(T task) {
        lock.lock();
        try {
            while (deque.size() == capcity) {
                log.info("等待加入队列{}", task);
                fullWait.await();
            }
            log.info("添加到任务队列 {} ", task);
            deque.add(task);
            emptyWait.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 带超时的阻塞获取元素
     *
     * @return
     */
    public T poll(long timeout, TimeUnit unit) {
        lock.lock();
        try {
            long nanos = unit.toNanos(timeout);
            try {
                while (deque.isEmpty()) {
                    if (nanos <= 0) {
                        return null;
                    }
                    nanos = emptyWait.awaitNanos(nanos);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            T t = deque.removeFirst();
            fullWait.signal();
            return t;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 带超时的阻塞添加
     *
     * @param timeout
     * @param unit
     * @return
     */
    public Boolean offer(long timeout, TimeUnit unit, T task) {
        lock.lock();
        try {
            long nano = unit.toNanos(timeout);
            try {
                while (deque.size() == capcity) {
                    if (nano <= 0) {
                        log.info("{}当前任务等待超时",task);
                        return false;
                    }
                    nano = fullWait.awaitNanos(nano);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("{}当前任务加入队列",task);
            deque.add(task);
            emptyWait.signal();
            return true;
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        lock.lock();
        try {
            return deque.size();
        } finally {
            lock.unlock();
        }
    }

    public void tryPut(RejectPolicy<T> rejectPolicy, T task) {
        lock.lock();
        try {
            if (deque.size() == capcity) {
                //执行拒绝策略
                log.info("执行拒绝策略");
                rejectPolicy.reject(this, task);
            } else {
                deque.add(task);
                emptyWait.signal();
            }
        } finally {
            lock.unlock();
        }
    }

}