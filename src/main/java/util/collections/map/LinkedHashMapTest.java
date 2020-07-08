package util.collections.map;

import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author liqiao47
 * @date 2020/4/9 20:52
 */
public class LinkedHashMapTest {
    /**
     * 简单实现LRU算法，只需要重写removeEldestEntry
     */
    @Test
    public void testLinkedHashMap() {
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(5, 0.75F, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
                //当LinkHashMap的容量大于等于5的时候,再插入就移除旧的元素
                return this.size() >= 5;
            }
        };
        map.put("aa", "bb");
        map.put("cc", "dd");
        map.put("ee", "ff");
        map.put("gg", "hh");
        print(map);
        map.get("cc");
        System.out.println("===================================");
        print(map);

        map.get("ee");
        map.get("aa");
        System.out.println("====================================");
        map.put("ss", "oo");
        print(map);
    }

    private void print(LinkedHashMap<String, String> source) {
        source.keySet().iterator().forEachRemaining(System.out::println);
    }

    @Test
    public void testLRU() {
        LRUCache<String, String> lruCache = new LRUCache<String, String>(4);
        lruCache.add("x", "X");
        lruCache.add("y", "Y");
        lruCache.add("z", "Z");
        lruCache.add("x", "_X");
        printf(lruCache);
        System.out.println("=========");
        lruCache.get("y");
        printf(lruCache);
        lruCache.add("a","A");
        lruCache.add("b","A");
        System.out.println("=========");
        printf(lruCache);

    }

    private void printf(LinkedHashMap<String, String> linkedHashMap) {
        linkedHashMap.entrySet().iterator().forEachRemaining(System.out::println);
    }
}
