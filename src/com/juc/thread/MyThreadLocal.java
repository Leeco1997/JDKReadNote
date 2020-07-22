package com.juc.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;


/**
 * @author liqiao
 * @date 2020/7/20 20:11
 * @description ThreadLocal:每一个线程都有一个
 */
@Slf4j
public class MyThreadLocal {
    public static void main(String[] args) {
        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        Thread thread = new Thread(()->{
            threadLocal.set("t1");
        });
        log.info(threadLocal.get());
        threadLocal.set("main");
        log.info(threadLocal.get());

    }
}
