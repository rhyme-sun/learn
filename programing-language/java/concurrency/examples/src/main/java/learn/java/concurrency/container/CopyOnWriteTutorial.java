package learn.java.concurrency.container;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * CopyOnWriteTutorial.
 */
public class CopyOnWriteTutorial {

    public static void main(String[] args) {
        // List
        CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();

        // Set
        CopyOnWriteArraySet copyOnWriteArraySet = new CopyOnWriteArraySet();
        ConcurrentSkipListSet concurrentSkipListSet = new ConcurrentSkipListSet();

        // Map
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();


    }
}