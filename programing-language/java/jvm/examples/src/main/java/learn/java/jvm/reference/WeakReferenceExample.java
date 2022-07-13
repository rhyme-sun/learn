package learn.java.jvm.reference;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * WeakReferenceExample.
 */
public class WeakReferenceExample {

    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        set.add(Arrays.toString(new int[]{1, 2}));
        set.add(Arrays.toString(new int[]{1, 2}));
        System.out.println(set);


        run1();
        run2();

        weakHashMap();
    }
    private static void run1() {
        WeakReference<Object> weakReference = new WeakReference<>(new Object());
        System.out.println(weakReference.get());
        System.gc();
        // 回收了 null
        System.out.println(weakReference.get());
    }

    private static void run2() {
        Object obj = new Object();
        WeakReference<Object> weakReference = new WeakReference<>(obj);
        System.out.println(weakReference.get());
        System.gc();
        // 未被回收，因为还有强引用指向
        System.out.println(weakReference.get());
    }

    private static void weakHashMap() {
        WeakHashMap<Object, Object> map = new WeakHashMap<>();
        map.put(new Object(), new Object());
        System.out.println(map.size());
        System.gc();
        System.runFinalization();
        System.out.println(map.size());
    }
}
