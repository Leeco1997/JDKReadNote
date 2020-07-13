package com.lang.String;


/**
 * @author liqiao
 * @date 2020/5/23
 */

public class StringBuilderTest {
    public static void main(String[] args) {
        StringBuilder stringBuilder = new StringBuilder("a");
        stringBuilder.append(1);
        stringBuilder.append(2.3);
        stringBuilder.append("abc");
        System.out.println(stringBuilder);

    }

}
