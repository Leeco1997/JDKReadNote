package com.juc.thread.threadpoll;



/**
 * @author liqiao
 * @date 2020/7/21 17:21
 * @description
 */
@FunctionalInterface
public interface RejectPolicy<T> {
    void reject(MyBlockingQueue<T> queue, T task);
}
