package util.collections.list;

import lang.clone.Person;
import org.junit.Test;

import java.io.*;
import java.lang.ref.PhantomReference;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author liqiao
 * @date 2020/7/05
 * @NotePath note/Collections.md
 */
public class ArrayListTest {
    @Test
    public void serializable() throws IOException, ClassNotFoundException {
        //序列化与反序列化
        ArrayList<String> arrayList = new ArrayList();
        arrayList.add("hello ");
        arrayList.add("world");
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("hello world"));
        os.writeObject(arrayList);

        ObjectInputStream is = new ObjectInputStream(new FileInputStream("hello world"));
        System.out.println(is.readObject());
        List<String> strings = Collections.synchronizedList(arrayList);

    }

    /**
     * 自定义实现顺序
     */
    @Test
    public void sortTest() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        //排序，不区分正负数
        arrayList.add(5);
        arrayList.add(-8);
        arrayList.add(-10);
        arrayList.add(6);
        arrayList.add(1);
        arrayList.add(0);
        Collections.sort(arrayList, new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                Integer x = Math.abs(a);
                return x.compareTo(Math.abs(b));
            }
        });
        System.out.println(arrayList);
    }

    /**
     * CopyOnWriteArrayList 读写分离
     */
    static private CopyOnWriteArrayList<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {
        for (int i = 0; i < 5000; i++) {
            copyOnWriteArrayList.add(i);
        }
        new Thread(new find(), "find1").start();
        new Thread(new update(), "update").start();
        new Thread(new find(), "find2").start();
        new Thread(new find(), "find3").start();
        new Thread(new find(), "find4").start();
        new Thread(new find(), "find5").start();
    }

    static class find implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "=" + copyOnWriteArrayList.get(10));
        }
    }

    static class update implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                copyOnWriteArrayList.remove(1);
                System.out.println("remove第" + i + "次");
            }
            System.out.println("update:" + copyOnWriteArrayList.get(10));
        }
    }


}
