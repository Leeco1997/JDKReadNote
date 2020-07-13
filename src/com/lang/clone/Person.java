package com.lang.clone;

import lombok.Data;
import lombok.Getter;

/**
 * @author liqiao
 * @date 2020/5/27
 * @description 深拷贝和浅拷贝的区别
 */
@Data
@Getter
public class Person implements Cloneable, Comparable<Person> {
    private String name;
    private int age;
    private Car car;

    public Person() {

    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    Person clone1() {
        try {
            //浅拷贝
            return (Person) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    Person clone2() {
        try {
            //深拷贝
            Person person = (Person) super.clone();
            person.car = (Car) car.clone();
            return person;

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }


    @Override
    public int compareTo(Person o) {
        return Integer.compare(this.age, o.getAge());
    }
}
