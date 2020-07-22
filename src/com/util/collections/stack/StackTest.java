package com.util.collections.stack;

import com.sun.xml.internal.bind.marshaller.NioEscapeHandler;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * @author liqiao47
 * @date 2020/4/9 20:52
 */
public class StackTest {

    public static void main(String[] args) {
        //iload_1表示将局部变量表中变量i的值复制一份，加载到操作数栈顶;
        //innc 1,1 指令则将局部变量表中变量i的值加1再写回局部变量表中变量i的位置;
        //istore_1则将`栈顶的数据`覆盖局部变量表中变量i的位置.
        //所以执行完这3个命令后，变量i的值并没有发生变化。
        int i = 0;
        for (int j = 0; j < 10; j++) {
            i = i + 5;
            // i = i;
            //i++;
        }
        System.out.println(i);
    }


}
