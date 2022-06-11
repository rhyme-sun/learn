package learn.algorithm.coding.design;

import java.util.HashMap;
import java.util.Map;

/**
 * 题目描述如下：
 * 请设计一种结构，其中包含类似于哈希表 put 和 get 分法，除此之外增加一个 setAll 分法，表示将哈希表中所有 key 对于的 value 值全部重新设置。
 * 要求全部方法的时间复杂度为 `O(1)`。
 */
public class SetAllMap {

    private Value setAll;
    private Map<String, Value> innerMap;

    public String get(String key) {
        Value value = innerMap.get(key);
        if (value == null) {
            return null;
        }
        return setAll.time > value.time ? setAll.value : value.value;
    }

    public void put(String key, String value) {
        innerMap.put(key, new Value(value, System.nanoTime()));
    }

    public void setAll(String value) {
        setAll = new Value(value, System.nanoTime());
    }

    public SetAllMap() {
        innerMap = new HashMap<>();
        setAll = new Value(null, -1);
    }

    static class Value {

        private String value;
        private long time;

        Value(String value, long time) {
            this.value = value;
            this.time = time;
        }
    }

    public static void main(String[] args) {
        SetAllMap map = new SetAllMap();
        map.put("a", "A");
        map.put("b", "B");
        System.out.println(map.get("a"));
        System.out.println(map.get("b"));
        System.out.println(map.get("c"));
        System.out.println();

        map.setAll("D");
        System.out.println(map.get("a"));
        System.out.println(map.get("b"));
        System.out.println(map.get("c"));
        System.out.println();

        map.put("a", "A");
        System.out.println(map.get("a"));
        System.out.println(map.get("b"));
        System.out.println(map.get("c"));
    }
}
