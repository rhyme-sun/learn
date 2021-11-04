package learn.java.basics.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * FailFastExample.
 */
public class FailFastExample {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");
//        final Iterator<String> iterator = list.iterator();
//        while (iterator.hasNext()) {
//            iterator.remove();
//        }
        for (String s : list) {
            // list.add("D");
            list.remove(s);
        }
    }
}