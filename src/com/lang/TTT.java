package com.lang;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liqiao
 * @date 2020/8/8 15:33
 * @description
 */

public class TTT {
    public static void main(String[] args) {
        isSuShu(7);
    }
    private static List<Integer> isSuShu(int num) {
        ArrayList<Integer> list = new ArrayList<>();
        if (num <= 1) {
            return null;
        }
        for (int i = 2; i <= num; i++) {
            boolean flag = true;
            for (int j = 2; j < i; j++) {
                if (i % j == 0) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                list.add(i);
            }
        }
        for (Integer n : list) {
            System.out.print(n + " ");
        }
        return list;
    }
    /**
     * 找到<=num的素数
     *
     * @param num
     * @return
     */
    private static List<Integer> isSuShu2(int num) {
        ArrayList<Integer> list = new ArrayList<>();
        if (num <= 1) {
            return null;
        }
        for (int i = 2; i <= num; i++) {
            boolean flag = true;
            for (int j = 2; j < i; j++) {
                if (i % j == 0) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                list.add(i);
            }
        }
        for (Integer n : list) {
            System.out.print(n + " ");
        }
        return list;
    }

}
