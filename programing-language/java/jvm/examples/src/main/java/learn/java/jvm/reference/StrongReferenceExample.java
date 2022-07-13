package learn.java.jvm.reference;

import java.util.ArrayList;
import java.util.List;

/**
 * StrongReferenceExample.
 * -Xms20m -Xmx20m
 */
public class StrongReferenceExample {

    static class StrongReferenceObject {}

    public static void main(String[] args) {
        List<StrongReferenceObject> list = new ArrayList<>();
        while (true) {
            list.add(new StrongReferenceObject());
        }
    }
}
