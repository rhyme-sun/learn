package learn.java.basics.collection;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

/**
 * LinkedHashMapExample.
 */
@Slf4j
public class LinkedHashMapExample {

    public static void main(String[] args) {
        LinkedHashMap<String, String> accessOrderedMap = new LinkedHashMap(16, 0.75F, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                // 实现自定义删除策略，否则行为就和普遍 Map 没有区别
                return size() > 3;
            }
        };
        accessOrderedMap.put("Project1", "Valhalla");
        accessOrderedMap.put("Project2", "Panama");
        accessOrderedMap.put("Project3", "Loom");
        accessOrderedMap.forEach((k, v) -> {
            log.info(k + ":" + v);
        });
        accessOrderedMap.get("Project2");
        accessOrderedMap.get("Project2");
        accessOrderedMap.get("Project3");

        log.info("Iterate over should be not affected:");
        for (Map.Entry<String, String> entry : accessOrderedMap.entrySet()) {
            log.info(entry.getKey() + ":" + entry.getValue());
            if (Objects.equals("Project2", entry.getKey())) {
                break;
            }
        }
        accessOrderedMap.forEach((k, v) -> {
            log.info(k + ":" + v);
        });
        accessOrderedMap.put("Project4", "Mission Control");
        log.info("Oldest entry should be removed:");
        accessOrderedMap.forEach((k, v) -> {
            log.info(k + ":" + v);
        });
    }
}