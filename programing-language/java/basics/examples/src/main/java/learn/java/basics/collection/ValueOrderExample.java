package learn.java.basics.collection;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import lombok.extern.slf4j.Slf4j;

/**
 * ValueOrderExample.
 */
@Slf4j
public class ValueOrderExample {

    public static void main(String[] args) {
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("a", "A");
        hashMap.put("c", "C");
        hashMap.put("b", "B");
        log.info("HashMap: {}", hashMap);

        Map<String, String> linkedHashMap = new LinkedHashMap(3, 0.75f, true);
        linkedHashMap.put("a", "A");
        linkedHashMap.put("c", "C");
        linkedHashMap.put("b", "B");
        // {a=A, c=C, b=B}
        log.info("LinkedHashMap: {}", linkedHashMap);
        linkedHashMap.get("a");
        // {c=C, b=B, a=A}
        log.info("LinkedHashMap: {}", linkedHashMap);

        Map<String, String> treeMap = new TreeMap<>();
        treeMap.put("a", "A");
        treeMap.put("c", "C");
        treeMap.put("b", "B");
        log.info("TreeMap: {}", treeMap);
    }
}