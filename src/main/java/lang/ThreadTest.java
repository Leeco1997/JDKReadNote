package lang;

/**
 * @author liqiao
 * @date 2020/4/29
 */

public class ThreadTest {
    public static void main(String[] args) {
        ObjectTest objectTest = new ObjectTest();

        Thread thread1 = new Thread() {
            @Override
            public void run() {
                synchronized (objectTest) {
                    try {
                        objectTest.wait();
                        System.out.println("a");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread1.start();
        Thread thread2 = new Thread() {
            @Override
            public void run() {
                synchronized (objectTest) {
                    try {
                        objectTest.notifyAll();
                        System.out.println("b");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread2.start();
    }
}
