package com.juc.thread;

import java.util.concurrent.locks.LockSupport;

/**
 * @author liqiao
 * @date 2020/7/21 11:57
 * @description 交替输出
 */

public class Park {
    static Thread t1;
    static Thread t2;
    static Thread t3;

    public static void main(String[] args) {
//        Park up = new Park();
//        t1 = new Thread(() -> {
//            up.print("a", t2);
//        });
//        t2 = new Thread(() -> {
//            up.print("b", t3);
//        });
//        t3 = new Thread(() -> {
//            up.print("c", t1);
//        });
//        t1.start();
//        t2.start();
//        t3.start();
//        // LockSupport.unpark(t1);
//        LockSupport.unpark(t2);
        new Park().searchRange(new int[]{0,0,1,2,3,3,4}, 2);
    }

    private void print(String str, Thread next) {
        for (int i = 0; i < 10; i++) {
            LockSupport.park();
            System.out.println(str);
            LockSupport.unpark(next);
        }
    }

    public int[] searchRange(int[] nums, int target) {
        if (nums.length == 0) {
            return new int[]{-1, -1};
        }
        if (nums.length == 1) {
            if (nums[0] == target) {
                return new int[]{0, 0};
            } else {
                return new int[]{-1, -1};
            }
        }
        int i = 0;
        int j = nums.length - 1;
        int mid = 0;
        while (i < j) {
            mid = (i + j) / 2;
            if (nums[mid] < target) {
                i = mid + 1;
            }
            else if (nums[mid] > target) {
                j = mid - 1;
            } else {
                j = mid;
            }
        }
        if (nums[i] == target) {
            j = i;
            int count = 0;
            while (j < nums.length-1 && nums[++j] == target) {
                count++;
            }
            System.out.println(i + " " + count);
            return new int[]{i, i + count};
        } else {
            return new int[]{-1, -1};
        }
    }

}
