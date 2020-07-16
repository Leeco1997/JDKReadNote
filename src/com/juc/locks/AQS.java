package com.juc.locks;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author liqiao
 * @date 2020/7/16 11:21
 * @description CAS && synchronized
 * cas 乐观锁---一直自选，消耗cpu
 * synchronized 乐观锁 ---wait的时候不消耗资源
 *
 *
 * AQS 原理: state + CLH（双向链表）
 * 实现的业务逻辑：
 * 1. 什么时候加入到队列？
 * 2. 什么时候能获取到锁？
 *
 */

public class AQS {
    public static void main(String[] args) {

    }
}
