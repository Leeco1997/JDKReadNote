package lang.String;

/**
 * @author liqiao
 * @date 2020/4/20
 */

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * javac StringTest.java 编译文件
 * javap -c StringTest 查看反编译后的结果
 *
 * <p>
 * 关于字符串的几个经典问题
 * 1.testEquals()中创建了几个对象？
 * 答：一个或者两个。区别在于字符串常量"abc"是否已经存在与常量池中。
 * <p>
 * 2.关于拼接前后的地址是否相同？
 * 答：弄清楚堆、栈、常量池里到底存储了哪些信息。
 * <p>
 * 3.字符串的+拼接实现原理？
 * <p>
 * 4.为什么String类型设计为不可变的？
 * 答：
 *
 * 5.如何设计一个类型是不可变的？
 *
 * 6. 为什么我们在使用HashMap的时候总是用String做key？
 * 答： 因为字符串是不可变的，当创建字符串时，它的它的hashcode被缓存下来，不需要再次计算
 * <p>
 */

/**
 * 1. inter()
 * 2. join()
 * 3. +拼接
 * 4. toCharArray
 * 5. split()
 */
public class StringTest {

    public static void main(String[] args) {
        // intern()
        String str1 = "abc";
        String str2 = new String("abc");
        String str3 = new String("abc");
        System.out.println(str1 == str2);
        //把常量池中 "abc" 的引用地址赋给str2
        str2 = str2.intern();
        System.out.println(str1 == str2);

        str3.intern();
        System.out.println(str1 == str3);

        //join()
        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList("to", "be", "or", "not", "to", "be"));
        String join = String.join("-", arrayList);
        System.out.println(join);



        //字符串常量相加，不会用到StringBuilder对象
        String name1 = "ch" + "ina";
        String name2 = "chin" + "a";
        //字符串的+操作其本质是new StringBuilder()对象, 进行append操作，拼接后调用toString()返回String对象
        String name3 = name1 + name2;
        String name4 = "chinachina";
        System.out.println(name1 == name2);
        System.out.println("name3 == name4:" + (name3 == name4));

        //toCharArray
        char[] chars = name4.toCharArray();

        byte[] bytes = name1.getBytes();

        //split
        String s1 = "abcaada";
        //[, bc, , d]
        System.out.println(Arrays.toString(s1.split("a")));
        //限制分割后的数组长度
        String s = "China,Sichuang,Chengdu";
        String[] data = s.split(",", 2);
        System.out.println("Name = "+data[0]); //China
        System.out.println("Address = "+data[1]); //Sichuang,Chengdu

        //indexof
        int i = name1.indexOf(1);


    }


    @Test
    public void testEquals() {
        String string = new String("123");
        String string1 = "123";
        System.out.println(string1 == string);
        System.out.println(string1.equals(string));
    }

    @Test
    public void testContact() {
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
        //false String.valueOf()在编译时期不能确定
        System.out.println("f==g: " + (f == g));
    }


}
