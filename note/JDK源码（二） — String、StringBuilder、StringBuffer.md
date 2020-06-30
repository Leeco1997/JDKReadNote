## JDK源码（二） — String/StringBuilder/StringBuffer

[TOC]

样例代码详见 `lang.String`

### 1. String

#### 1.1 继承关系

```java
public final class String
    implements java.io.Serializable, Comparable<String>, CharSequence
```

+ final 修饰

+ 实现了 Comparanle和CharSequence的基本方法

#### 1.2 成员变量

```java
   //jdk1.9以后，使用 byte value[]
    private final char value[];

    private int hash; // Default to 0

    private static final long serialVersionUID = -6849794470754667710L;

    private static final ObjectStreamField[] serialPersistentFields =
        new ObjectStreamField[0];
```

#### 1.3 构造方法

##### 1.3.1 无参构造

```java
   public String() {
        //初始化空字符串
        this.value = "".value;
    }
```

##### 1.3.2 有参构造

```java
    public String(String original) {
        this.value = original.value;
        //计算hashcode,由于String是不可变得，所以只需要计算一次
        this.hash = original.hash;
    }

   public String(char value[]) {
        this.value = Arrays.copyOf(value, value.length);
    }

    public String(StringBuffer buffer) {
        //加锁
        synchronized(buffer) {
            this.value = Arrays.copyOf(buffer.getValue(), buffer.length());
        }
    }
```

#### 1.4  常用方法

##### 1.4.1 inter()

```java
     /* 
     * When the intern method is invoked, if the pool already contains a
     * string equal to this {@code String} object as determined by
     * the {@link #equals(Object)} method, then the string from the pool is
     * returned. Otherwise, this {@code String} object is added to the
     * pool and a reference to this {@code String} object is returned.
     * /
     
     /* 如果常量池中已经有这个字符串常量，则直接返回它的引用地址，如果没有，则在常量池中新建
     *  这个字符串，然后返回引用对象地址。
     */
     
        String str1 = "abc";
        String str2 = new String("abc");
        String str3 = new String("abc");
        System.out.println(str1 == str2); //false
        //把常量池中 "abc" 的引用地址赋给str2
        str2 = str2.intern();
        System.out.println(str1 == str2); //true

        str3.intern();
        System.out.println(str1 == str3); //false

```



##### 1.4.2 join()

```java
 //String.join是jdk1.8中新增的  
ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList("to", "be", "or", "not", "to", "be"));
        String join = String.join("-", arrayList);
		//输出：to-be-or-not-to-be
        System.out.println(join);
```



##### 1.4.3 toCharArray

```java
    public char[] toCharArray() {
        // Cannot use Arrays.copyOf because of class initialization order issues
        char result[] = new char[value.length];
        System.arraycopy(value, 0, result, 0, value.length);
        return result;
    }
		//Arrays.copyOf会先申请空间，然后复制元素。
```



##### 1.4.4 split()

```java

        String s1 = "abcaada";
        //最后一个a会删除 [, bc, , d]
        System.out.println(Arrays.toString(s1.split("a")));
        //限制分割后的数组长度
        String s = "China,Sichuang,Chengdu";
        String[] data = s.split(",", 2);
        System.out.println("Name = "+data[0]); //China
        System.out.println("Address = "+data[1]); //Sichuang,Chengdu
```



##### 1.4.5 equals()

```java
   public boolean equals(Object anObject) {
       if (this == anObject) { //先判断地址是否相同
            return true;
        }
        if (anObject instanceof String) //判断对象类型
   			······
   }
   }
```



#### 1.5 常见面试问题

##### 1.5.1 创建对象个数

```java
        String string = new String("123");
        String string1 = "123";
        System.out.println(string1 == string); // false
        System.out.println(string1.equals(string)); //true
```

**问：**  String string = new String("123")；中创建了几个对象？

##### 1.5.2 拼接前后地址比较

```java
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
        //false 实际是使用了StringBuilder的append(),然后toString.
        System.out.println("c==e: " + (c == e));
        //false String.valueOf()在编译时期不能确定
        System.out.println("f==g: " + (f == g));
    }
```

**关于字符串的+&& 常量的+**

```java
public viod StringTest(){
    
   //字符串常量相加，系统在编译的时候会优化，自动拼接在一起
        String name1 = "ch" + "ina";
        String name2 = "chin" + "a";
    //字符串的+操作其本质在字符串缓冲区中new StringBuilder()对象,进行append操作，toString()返回
        String name3 = name1 + name2;
        String name4 = "chinachina";
        System.out.println(name1 == name2);
        System.out.println("name3 == name4:" + (name3 == name4));
    }   
```

**使用** `javac StringTest.java`编译，然后`javap -c StringTest`查看虚拟机指令

```swift
    Code:
       0: ldc           #2                  // String china  (放入常量池)
       2: astore_1                          //把引用放入局部变量表                
       3: ldc           #2                  // String china
       5: astore_2                           
       6: new           #3                  // class java/lang/StringBuilder(新建对象)
       9: dup                               //复制一份，栈顶部有两份一样的引用
      10: invokespecial #4                  // Method java/lang/StringBuilder."<init>":()V
      13: aload_1
      14: invokevirtual #5                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
      17: aload_2
      18: invokevirtual #5                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
      21: invokevirtual #6                  // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
      24: astore_3
      25: ldc           #7                  // String chinachina

```



##### 1.5.3 String类型设计为不可变得好处？

+ 字符串常量池的需要，相同的字符串，常量池只会保存一份；
+ HsahMap的key一般设置为String,因为hashcode只需要计算一次；
+ 安全性，可以设置为密码，不会被修改；
+ 线程安全。

##### 1.5.4 如何设计一个不可变的类？

+ 使用final修饰；

+ 内部变量使用private修饰；

+ 不提供set方法；

+ get返回的是对象的拷贝，接触引用关系；

  





