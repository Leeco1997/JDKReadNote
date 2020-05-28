package lang.clone;

/**
 * @author liqiao
 * @date 2020/5/27
 * @description
 */

public class CloneTest {
    public static void main(String[] args) {
        Car car = new Car();
        car.setColor("red");
        car.setPrice(10_0000);
        Person person = new Person();
        person.setAge(20);
        person.setName("奥力给");
        person.setCar(car);
        System.out.println(person.hashCode() + person.toString());
        Person clone = (Person) person.clone();
        clone.setName("皮卡丘");
    }
}
