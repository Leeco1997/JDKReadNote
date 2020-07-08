package juc.thread;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author liqiao
 * @date 2020/7/7 20:16
 * @description 创建线程的方式
 * 1. new Thread()
 * 2. runnable方法可以使用匿名内部类，也可以重写一个类。
 * 3. 使用lambda表达式
 */
@Slf4j(topic = "thread")
public class ThreadTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //第一种
        Runnable runnable = () -> {
            log.info("running1");
        };
        new Thread(runnable, "t1").start();

        //第二种
        new Thread("t2") {
            @Override
            public void run() {
                log.info("running2");
            }
        }.start();

        //使用Future
        FutureTask futureTask = new FutureTask<>(() -> {
            log.info("t3 begin");
            Thread.sleep(1000);
            log.info("t3 end");
            return 100;
        });
        Thread thread = new Thread(futureTask, "t3");
        thread.run();
        thread.start();
        //阻塞获取
        log.info(String.valueOf(futureTask.get()));
        log.info("end ");
    }

    @Test
    public void sleepTest() {
        Thread t4 = new Thread(() -> {
            log.info("t4 running");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t4");
        t4.start();
        try {
            Thread.sleep(500);
            t4.interrupt();
            log.info("{}", t4.getState());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
