package com.util.collections.list;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author liqiao47
 * @date 2020/4/10 17:03
 * @description 链表和数组的性能测试
 */

public class SpeedTest {
    public static void main(String[] args) {
        ArrayList<Integer> arrayList = new ArrayList<Integer>(Collections.nCopies(10_000_000, 8));
        //LinkedList<Integer> arrayList = new LinkedList<>(Collections.nCopies(10_000_000, 8));

        long begin = System.currentTimeMillis();
        arrayList.remove(2500_000);
        long end = System.currentTimeMillis();
        System.out.println(end - begin);

        begin = System.currentTimeMillis();
        arrayList.remove(2500_000);
        end = System.currentTimeMillis();
        System.out.println(end - begin);

        begin = System.currentTimeMillis();
        arrayList.remove(2500_000);
        end = System.currentTimeMillis();
        System.out.println(end - begin);

    }
}
