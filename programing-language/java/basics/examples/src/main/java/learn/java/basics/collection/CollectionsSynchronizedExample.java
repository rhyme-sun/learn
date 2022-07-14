package learn.java.basics.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * CollectionsSynchronizedExample.
 */
public class CollectionsSynchronizedExample {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        List<String> syncList = Collections.synchronizedList(list);
    }
}
