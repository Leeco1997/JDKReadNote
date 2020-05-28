package lang;



import java.util.concurrent.TimeUnit;

/**
 * @author liqiao
 * @date 2020/4/25
 * @description 关于Object类中几个方法。
 */


public class ObjectTest {
    public static void main(String[] args) {
        /*
         * 输出结果:
         * Thread-0 is waiting for lock
         * Thread-0 gets the lock
         * Thread-1 is waiting for lock
         * Thread-0 is doing something
         * Thread-1 gets the lock
         * Thread-1 is doing something
         * Thread-0 finish
         * ObjectMonitor对象中有两个队列：_WaitSet 和 _EntryList,分别存放wait状态和等待锁状态的线程。
         */
        Object object = new Object();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " is waiting for lock");
            synchronized (object) {
                try {
                    System.out.println(Thread.currentThread().getName() + " gets the lock");
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println(Thread.currentThread().getName() + " is doing something");
                    object.wait(100);
                    System.out.println(Thread.currentThread().getName() + " finish");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " is waiting for lock");
            synchronized (object) {
                try {
                    System.out.println(Thread.currentThread().getName() + " gets the lock");
                    object.notify();
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println(Thread.currentThread().getName() + " is doing something");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void finalize() throws Throwable {
        try{
            System.out.println("finalize success");
        }
        finally {
            super.finalize();
        }
    }
}
