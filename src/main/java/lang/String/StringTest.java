package lang.String;


/**
 * javac StringTest.java 编译文件
 * javap -c StringTest 查看反编译后的结果
 * <p>
 * 关于字符串的几个经典问题
 * 1.test()中创建了几个对象？
 * 答：一个或者两个。区别在于字符串常量"abc"是否已经存在与常量池中。
 * <p>
 * 2.关于拼接前后的地址是否相同？
 * 答：弄清楚堆、栈、常量池里到底存储了哪些信息。
 *
 * <p>
 */
public class StringTest {

    public static void main(String[] args) {
//        new StringTest().testEquals();
//        new StringTest().testContact();
        String a = "a     c";
        System.out.println(a.split("  ").length);
    }

    private void testEquals() {
        String string = new String("123");
        String string1 = "123";
        System.out.println(string1 == string);
        System.out.println(string1.equals(string) == true);
    }

    private void testContact() {
        String a = "hello girl";
        final String b = "hello";
        String d = "hello";

        String c = "hello" + " girl";
        String e = d + " girl";

        final String f = "hello" + String.valueOf(" girl");
        final String g = "hello girl";
        //true  b,d指向常量池中的同一个位置
        System.out.println("d==b: " + (d == b));
        //true  a,c指向常量池中的同一个位置,c在编译时期完成自动拼接。
        System.out.println("a==c: " + (a == c));
        //false e是由栈中d的地址加上常量的地址。
        System.out.println("c==e: " + (c == e));
        //String.valueOf()在编译时期不能确定
        System.out.println("f==g: " + (f == g));
    }


    public String[] testSplit(String string, int limit) {
        return null;

    }

}
