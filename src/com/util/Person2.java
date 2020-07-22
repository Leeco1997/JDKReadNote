package com.util;

import lombok.Data;

/**
 * @author liqiao
 * @date 2020/7/18 21:41
 * @description 使用builder设计模式，builder是一个静态类
 */
@Data
public class Person2 {
    private String name;
    private int age;

    private Person2(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
    }

    public static class Builder {

        private String name;
        private int age;

        public Builder() {
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Person2 build() {
            return new Person2(this);
        }
    }

}

