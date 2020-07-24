package com.juc.locks;

import java.util.concurrent.Semaphore;

/**
 * @author liqiao
 * @date 2020/7/23 22:28
 * @description 信号量，常常用于限流，限制数据库的连接数目
 */

public class SemaphoreTest {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);
    }
}
