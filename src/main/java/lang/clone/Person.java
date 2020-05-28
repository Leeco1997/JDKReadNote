package lang.clone;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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

    @Override
    protected Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

}
