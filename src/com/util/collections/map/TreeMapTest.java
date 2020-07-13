package com.util.collections.map;

import com.lang.clone.Person;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liqiao
 * @date 2020/7/7 10:18
 * @description
 */

public class TreeMapTest {
    public static void main(String[] args) {
        ConcurrentHashMap<Integer, Integer> concurrentHashMap = new ConcurrentHashMap<>(5);
        for (int i = 0; i < 5; i++) {
            concurrentHashMap.put(i, -i);
        }
        //
        Set<Map.Entry<Integer, Integer>> entries = concurrentHashMap.entrySet();
        for (Map.Entry<Integer,Integer> entry :entries){
            System.out.println(entry.getKey() + entry.getValue());
        }
        //
        HashMap<Integer, Integer> integerIntegerHashMap = new HashMap<>();


    }

    /**
     * 实现排序
     */
    @Test
    public void compare() {
        TreeMap<Person, Integer> treeMap = new TreeMap<>();
        treeMap.put(new Person("a", 10), 101);
        treeMap.put(new Person("f", 9), 102);
        treeMap.put(new Person("b", 8), 103);
        treeMap.put(new Person("d", 6), 104);

        treeMap.entrySet().iterator().forEachRemaining(System.out::println);
    }
}
