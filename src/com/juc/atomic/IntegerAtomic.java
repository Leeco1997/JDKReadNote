package com.juc.atomic;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author liqiao
 * @date 2020/7/19 11:21
 * @description
 */
public class IntegerAtomic {
    public static void main(String[] args) {
        AtomicInteger integer = new AtomicInteger(5);
        System.out.println(integer.incrementAndGet());
        System.out.println(integer.getAndIncrement());
        System.out.println(integer.weakCompareAndSet(2, 5));
        System.out.println(integer.updateAndGet(i -> i * 3));
      //  AtomicStampedReference<BigDecimal> number = new AtomicStampedReference<BigDecimal>();

    }

}

/**
 * 累加操作
 */
class Add {
    public static void main(String[] args) {
        LongAdder longAdder = new LongAdder();

    }

}