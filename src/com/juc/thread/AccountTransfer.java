package com.juc.thread;


/**
 * @author liqiao
 * @date 2020/7/9 16:20
 * @description todo:模拟银行转账
 */

public class AccountTransfer {
    public static void main(String[] args) {
        Window window = new Window(100);

    }

    static class Window{
        int count ;
        Window(int count){
            this.count = count;
        }

        public int getCount(){
            return count;
        }

        public boolean sell(int amount){
            if (count > amount) {
                this.count -=amount;
            return true;
            }
            return false;
        }


    }
}
