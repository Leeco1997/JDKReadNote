package lang.clone;

import lombok.Data;

/**
 * @author liqiao
 * @date 2020/5/27
 * @description 深拷贝和浅拷贝的区别
 */
@Data
public class Person implements Cloneable {
    private String name;
    private int age;
    private Car car;

    protected Person clone1() {
        try {
            //浅拷贝
            return (Person) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected Person clone2() {
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
}
