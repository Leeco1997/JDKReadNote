package juc.locks;

/**
 * @author liqiao
 * @date 2020/6/23 20:57
 * @description
 */

public class SynchronizedTest {
    public static void main(String[] args) {

    }
    //可重入锁
    public synchronized void test(){
        System.out.println("do something.");
        synchronized (this){
            System.out.println("……");
        }
    }
}
