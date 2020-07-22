package com.juc.locks;

import java.util.concurrent.CountDownLatch;

/**
 * @author liqiao
 * @date 2020/7/20 22:46
 * @description
 */

public class CountDownLatchTest {
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(100);
        countDownLatch.countDown();
    }
}
