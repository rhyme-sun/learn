package learn.java.basics.collection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * HashMapExample.
 */
@Slf4j
public class HashMapExample {

    public static void main(String[] args) {
        Map map1 = new HashMap();
        // Threshold: 0, Capacity: 16
        printField(map1);
        map1.put("a", "A");
        // Threshold: 12, Capacity: 16
        printField(map1);
        Map map2 = new HashMap(15);
        // Threshold: 16, Capacity: 16
        printField(map2);
        Map map3 = new HashMap(21);
        // Threshold: 32, Capacity: 32
        printField(map3);
        Map map4 = new HashMap(21, 0.5f);
        // Threshold: 32, Capacity: 32
        printField(map4);
    }

    static void printField(Map map) {
        try {
            final Class<HashMap> hashMapClass = HashMap.class;
            final Field threshold = hashMapClass.getDeclaredField("threshold");
            threshold.setAccessible(true);
            final Object thresholdValue = threshold.get(map);

            final Method capacity = hashMapClass.getDeclaredMethod("capacity");
            capacity.setAccessible(true);
            final Object capacityValue = capacity.invoke(map);
            log.info("Threshold: {}, Capacity: {}", thresholdValue, capacityValue);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}