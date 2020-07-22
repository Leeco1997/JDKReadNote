package com.juc.atomic;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author liqiao
 * @date 2020/7/21 16:28
 * @description
 */
@Slf4j
public class MyUnsafe {
    static Unsafe unsafe;

    static Unsafe getUnsafe() {
        return unsafe;
    }

    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            unsafe = (Unsafe) theUnsafe.get(null);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws NoSuchFieldException, ParseException {
        Field id = Student.class.getDeclaredField("id");
        Field name = Student.class.getDeclaredField("name");
        // 获得成员变量的偏移量
        long idOffset = MyUnsafe.unsafe.objectFieldOffset(id);
        long nameOffset = MyUnsafe.unsafe.objectFieldOffset(name);

        Student student = new Student();
        // 使用 cas 方法替换成员变量的值
        MyUnsafe.unsafe.compareAndSwapInt(student, idOffset, 0, 20);
        MyUnsafe.unsafe.compareAndSwapObject(student, nameOffset, null, "张三");
        System.out.println(student);

    }
}

@Data
class Student {
    volatile int id;
    volatile String name;
}
