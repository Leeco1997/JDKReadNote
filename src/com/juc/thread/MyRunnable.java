package com.juc.thread;

/**
 * @author liqiao
 * @date 2020/6/28 11:20
 * @description
 */

public class MyRunnable implements Runnable {
    private int i ;
    public MyRunnable(int i ){
        this.i = i;
    }
    @Override
    public void run() {
        System.out.println("第"+i+"个任务开始：");
        method();
        System.out.println("第"+i+"个任务结束#");

    }

    private void method(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
