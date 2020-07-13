package com.util.collections.map;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author liqiao
 * @date 2020/7/6 21:33
 * @description LRU算法
 */

public class LRUCache<K, V> extends LinkedHashMap<K, V> {
    final int maxSize;

    public LRUCache(int maxSize) {
        super(maxSize, 0.75F, false);
        this.maxSize = maxSize;
    }

    public V add(K key, V value) {
        return put(key, value);
    }

    public V get(K key, V value) {
        return get(key);
    }

    public boolean exist(K key) {
        return containsKey(key);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > maxSize;
    }


}
