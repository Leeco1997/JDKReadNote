package com.lang;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author liqiao
 * @date 2020/8/8 15:07
 * @description
 */

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();
        while (num != 0) {
            num--;
            int count = scanner.nextInt();
            int[] arr = new int[count];
            for (int i = 0; i < count; i++) {
                arr[i] = scanner.nextInt();
            }
            Arrays.sort(arr);
            System.out.println(arr[0]);
        }
    }



}



