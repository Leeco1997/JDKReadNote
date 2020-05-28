package lang;

/**
 * @author liqiao
 * @date 2020/4/20
 */

public class IntegerTest {
    public static void main(String[] args) {
        Integer a = 10;
        Integer b = 10;
        Integer c = 200;
        Integer d = 200;
        while (true) {

            System.out.println((a == b) + " " + a.hashCode() + " " + b.hashCode());
            System.out.println((c == d) + " " + c.hashCode() + " " + d.hashCode());
        }
    }

    public int printf(int i, String string) {
        return i;
    }


}
