package com.juc.thread;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

/**
 * @author liqiao
 * @date 2020/7/9 21:36
 * @description 锁消除
 *
 * //默认开启锁消除
 * Benchmark         Mode  Cnt  Score   Error  Units
 * DeleteLockTest.a  avgt    5  2.267 ± 0.104  ns/op
 * DeleteLockTest.b  avgt    5  2.242 ± 0.199  ns/op
 *
 * //关闭锁消除
 * Benchmark         Mode  Cnt   Score   Error  Units
 * DeleteLockTest.a  avgt    5   2.456 ± 0.780  ns/op
 * DeleteLockTest.b  avgt    5  25.076 ± 7.844  ns/op
 */

@Fork(value = 1,jvmArgsAppend = {"-server", "-Xms4g", "-Xmx4g", "-Xmn1536m", "-XX:-EliminateLocks"})
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 3)
@Measurement(iterations = 5)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class DeleteLockTest {
    static int x = 0;
    @Benchmark()
    public void a() {
        x++;
    }

    /**
     * jIT 逃逸分析
     */
    @Benchmark
    public void b() {
        Object o = new Object();
        synchronized (o) {
            x++;
        }
    }

}
