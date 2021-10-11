package learn.java.concurrency.container;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

/**
 * SyncContainer.
 */
public class SyncContainer {

    public static void main(String[] args) throws InterruptedException {
        // atomicOperationCombination();
        syncIterator();
    }

    private void syncContainer() {
        final List<Object> syncArrayList = Collections.synchronizedList(new ArrayList<>());
        final Set<Object> syncHashSet = Collections.synchronizedSet(new HashSet<>());
        final Map<Object, Object> syncHashMap = Collections.synchronizedMap(new HashMap<>());
    }

    /**
     * 原子操作的组合操作不具有原子性
     */
    private static void atomicOperationCombination() throws InterruptedException {
        final List<Object> syncArrayList = Collections.synchronizedList(new ArrayList<>());
        final Object value = new Object();
        Thread t1 = new Thread(() -> {
            addIfNotExist(syncArrayList, value);
        });
        Thread t2 = new Thread(() -> {
            addIfNotExist(syncArrayList, value);
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        // expect 1, but 2
        System.out.println(syncArrayList.size());
    }

    private static boolean addIfNotExist(final List<Object> syncArrayList, final Object value) {
        if (!syncArrayList.contains(value)) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            syncArrayList.add(value);
            return true;
        }
        return false;
    }

    private static void syncIterator() throws InterruptedException {
        final List<String> syncArrayList = Collections.synchronizedList(new ArrayList<>());
        syncArrayList.add("A");
        syncArrayList.add("B");
        syncArrayList.add("C");

        Thread t = new Thread(() -> {
            syncArrayList.remove("C");
            syncArrayList.add("D");
            //System.out.println(syncArrayList);
        });
        t.start();

        synchronized (syncArrayList) {
            final Iterator iterator = syncArrayList.iterator();
            while (iterator.hasNext()) {
                Thread.sleep(100);
                System.out.println(iterator.next());
            }
        }
        System.out.println(syncArrayList);
    }
}