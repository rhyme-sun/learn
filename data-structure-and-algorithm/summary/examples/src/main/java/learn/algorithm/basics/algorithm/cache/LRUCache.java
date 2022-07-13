package learn.algorithm.basics.algorithm.cache;

import java.util.LinkedHashMap;

/**
 * LRUCache
 * 淘汰调最近最少使用的缓存对象。
 */
public class LRUCache {

    private int capacity;
    private LinkedHashMap<Integer, Integer> cache;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new LinkedHashMap<>();
    }

    public int get(int key) {
        if (!cache.containsKey(key)) {
            return -1;
        }
        recent(key);
        return cache.get(key);
    }

    public void put(int key, int val) {
        if (cache.containsKey(key)) {
            cache.put(key, val);
            recent(key);
            return;
        }
        if (cache.size() >= this.capacity) {
            // 链表头部就是最久未使用的 key
            int oldestKey = cache.keySet().iterator().next();
            cache.remove(oldestKey);
        }
        cache.put(key, val);
    }

    private void recent(int key) {
        // 删除 key，重写放到队尾
        cache.put(key, cache.remove(key));
    }
}
