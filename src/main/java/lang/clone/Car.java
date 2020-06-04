package lang.clone;

import lombok.Data;

/**
 * @author liqiao
 * @date 2020/5/27
 * @description
 */

@Data
public class Car implements Cloneable{
    private int price;
    private String color;


    public Car(int price, String color) {
        this.price = price;
        this.color = color;
    }

    /**
     * 只有深拷贝才需要对每一层内部对象重写clone
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
