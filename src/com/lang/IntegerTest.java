package com.lang;

import java.util.HashMap;

/**
 * @author liqiao
 * @date 2020/4/20
 */

public class IntegerTest {
    public static void main(String[] args) {
        //Integer是线程安全的   private final int value;
        Integer a = 10;
        Integer b = 10;
        Integer c = 200;
        Integer d = 200;
        int[] nums1 = new int[]{1, 2, 3, 41, 5};
        int[] nums2 = new int[]{1, 2, 3, 41, 5};
        HashMap<int[], Integer> hashMap = new HashMap<>();
        hashMap.put(nums1, 1);
        hashMap.put(nums2, 2);
        hashMap.put(nums2, 3);
        System.out.println(hashMap.get(nums1));
        System.out.println(hashMap.get(nums2));

        System.out.println((a == b) + " " + a.hashCode() + " " + b.hashCode());
        System.out.println((c == d) + " " + c.hashCode() + " " + d.hashCode());
    }


}
