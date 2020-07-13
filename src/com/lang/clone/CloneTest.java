package com.lang.clone;

/**
 * @author liqiao
 * @date 2020/5/27
 * @description 浅拷贝 && 深拷贝
 */

public class CloneTest {
    public static void main(String[] args) {
        //定义内部对象car的属性
        Car car = new Car(10_0000, "red");
        Person person = new Person();
        person.setAge(20);
        person.setName("奥力给");
        person.setCar(car);
        System.out.println("============原数据============");
        //如果没有重写hashcode，System.identityHashCode和hashcode的值是一样的，都是一个Integer的内存地址。
        System.out.println("person: " + System.identityHashCode(person) + " " + person.hashCode() + " " + person.toString());
        //浅拷贝
        Person shadowClone = person.clone1();
        Car cloneCar = shadowClone.getCar();
        cloneCar.setPrice(20_0000);
        cloneCar.setColor("black");
        shadowClone.setAge(25);
        shadowClone.setName("皮卡丘");
        System.out.println("===========浅拷贝=============");
        System.out.println("clone: " + System.identityHashCode(shadowClone) + " " + shadowClone.hashCode() + " " + shadowClone.toString());
        //person的 car对象属性都发生了改变，但是name,age均没改变
        System.out.println("person: " + System.identityHashCode(person) + " " + person.hashCode() + " " + person.toString());

        System.out.println("===========对象拷贝=============");
        Person personClone = person;
        Car personCar = person.getCar();
        personClone.setName("洛天依");
        person.setAge(17);
        personCar.setColor("white");
        System.out.println("personClone:"+System.identityHashCode(personClone) + " " + personClone.hashCode()+" "+personClone.toString());
        System.out.println("person: " + System.identityHashCode(person) + " " + person.hashCode() + " " + person.toString());

        System.out.println("===========深拷贝=============");
        Person deepclone = person.clone2();
        Car deepcloneCar = deepclone.getCar();
        deepcloneCar.setColor("grey");
        deepcloneCar.setPrice(30_0000);
        deepclone.setName("马里奥");
        deepclone.setAge(10);
        System.out.println("deepClone:"+System.identityHashCode(deepclone) + " " + deepclone.hashCode()+" "+deepclone.toString());
        System.out.println("person: " + System.identityHashCode(person) + " " + person.hashCode() + " " + person.toString());


    }
}
