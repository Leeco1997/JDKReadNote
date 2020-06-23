## JDK源码（一）— 基础类
[TOC]

### 1. Object
> Object是所有类的超类，所有的对象，包括数组，都实现了这个类中的方法。
#### 1.1 源码

这个源码的理解，基本就来源于官方注释了，基本都是native方法。。。。。。。

```java
public class Object {

    private static native void registerNatives();
    //对象初始化时会先调用静态代码块的方法，注册本地方法
    static {
        registerNatives();
    }

   	// 返回此Object的运行时Class类
    public final
        
        native Class<?> getClass();

    /** 
    *  that equal objects must have equal hash codes.
    *  If two objects are equal according to the {@code equals(Object)}
    *  method, then calling the {@code hashCode} method on each of
    *  the two objects must produce the same integer result.
    *  所以，两个相等的对象必须拥有相同的hashcode。   
    */
     
    // 返回对象的hashcode
    public native int hashCode();
    
    /**比较的是对象的地址是否相等
    * 需要满足 自反性、对称性、传递性、一致性、非空性。
    * non-null: x.equals(null) == false
    */
    public boolean equals(Object obj) {
        return (this == obj);
    }

    /** 创建并返回一个对象的复制。满足：
     *  x.clone() != x
     *  x.clone().getClass() == x.getClass()
     *  x.clone().equals(x)
     *  自定义类使用clone方法时需要实现Cloneable接口，否则会抛出CloneNotSupportedException
     * 默认实现浅拷贝 [后文有详细介绍]
     */
    protected native Object clone() throws CloneNotSupportedException;

    // 返回运行时类名@引用地址(?)
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode());
    }

    // 唤醒在此对象监视器等待的单个线程，在多线程的情况下，随机唤醒一个线程
    public final native void notify();

    // 唤醒在此对象监视器等待的所有线程，并不决定哪个线程能够获取到monitor
    public final native void notifyAll();

    /**
     * 调用wait()方法，会交出此对象的monitor(释放锁)；线程的任何其他对象在线程等待时仍处于锁定状态。
     * 唤醒的条件，满足任一即可：
     * 1. 调用notify()或者notifyAll()
     * 2. 超时
     * 3. 其他线程中调用该线程的interrupt()方法
     */
    public final native void wait(long timeout) throws InterruptedException;

    /**
     * 以下两个wait()方法是对native wait()的重载
     * 区别：用毫微秒度量实际时间量，可以动态增加阻塞时间
     */
    public final void wait(long timeout, int nanos) throws InterruptedException {
        if (timeout < 0) {
            throw new IllegalArgumentException("timeout value is negative");
        }

        if (nanos < 0 || nanos > 999999) {
            throw new IllegalArgumentException(
                                "nanosecond timeout value out of range");
        }

        if (nanos > 0) {
            timeout++;
        }

        wait(timeout);
    }

    /**
     * The current thread must own this object's monitor.
     * 不会发生超时现象，所以唤醒方式只有两种
     */
    public final void wait() throws InterruptedException {
        wait(0);
    }

    /**
     * 用来释放长期不被引用的对象
     * 在启用某个对象的 finalize 方法后，该对象有最后一次自救的机会，只要重新与引用链上的一个对象关联即可      * 任何一个对象的finalize()只会被系统调用一次
     * 这个方法使用 protected修饰的，所以不同包的非子类不能使用
     */
    protected void finalize() throws Throwable { }
}

```
#### 1.2 Clone() — 软拷贝&浅拷贝

呃……因为这个方法我最陌生，所以就专门拎粗来瞅瞅。测试代码详见`java.lang.CloneTest`

**浅拷贝 (Object的默认clone类型)**

>对基本数据类型进行值的传递，对引用数据类型进行引用地址的拷贝。也就是说，如果其中一个对象修改了这个地址的值，就会影响到所有的拷贝对象。

```java
 protected Person clone() {
        try {
            //浅拷贝
            return (Person) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
```

**深拷贝**

> 对基本数据类型进行值传递，对引用数据类型，创建一个新的对象，并复制其内容。对于有多层对象的，每个对象都需要实现 `Cloneable` 并重写 `clone()` 方法，进而实现了对象的串行层层拷贝。

```java
 protected Person clone() {
        try {
            //深拷贝 每一层都需要拷贝 
            Person person = (Person) super.clone();
            person.car = (Car) car.clone();
            return person;

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
```



**对象拷贝**

>对象拷贝并没有生成新的对象，引用地址都是一样的。

```java
    Person person = new Person();
    Person personClone = person;
```



#### 1.3 为什么尽量不使用finalize()？

> 终结方法（finalizer）通常是不可预测的，也是很危险的，一般情况下是不必要的。是使用终结方法会导致行为不稳定、降低性能，以及可移植性问题。                                          — 《Effective Java  V2   P24》

+ finalize()不会被立即执行，因为需要先将对象变成不可达对象，而这期间耗费的时间是任意长的。
+ 如果未被捕获的异常在finalize()过程中被抛出来，是会被忽略的，而且finalize()也会被终止。
+ 使用终结方法有一个非常严重的性能损失。某测试结果，创建和销毁一个简单对象的时间大约为5.6ns、增加一个终结方法使时间增加到了2400ns。换句话说，用终结方法创建和销毁对象慢了大约430倍。

**------  那么，如何才能做到不显示的编写终结方法呢？**

>**答**： 提供一个显示的终止方法，并要求该类的客户端在每个实例不再有用的时候调用这个方法。*

**例如：** InputStream、OutputStream、java.sql.connection的close。

**终结方法链不会自动执行，需要手动调用**

```java
   @Override
    protected void finalize() throws Throwable {
        try{
            ……… //finalize subclass
        }
        finally{
        super.finalize();    
        }       
    }
```

#### 1.4 System.identityHashCode() && hashcode

```java
 /**
     * Returns the same hash code for the given object as
     * would be returned by the default method hashCode(),
     * whether or not the given object's class overrides
     * hashCode().
     * The hash code for the null reference is zero.
  */
 // 如果没有重写hashcode,返回的值和hashcode一样，都是一个Integer的内存地址。
 // 空引用的值为0
 public static native int identityHashCode(Object x);
```


