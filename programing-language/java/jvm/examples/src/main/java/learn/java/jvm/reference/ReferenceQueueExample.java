package learn.java.jvm.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

/**
 * ReferenceQueueExample.
 */
public class ReferenceQueueExample {

    public static void main(String[] args) {
        ReferenceQueue refQueue = new ReferenceQueue<>();

        Object counter = new Object();
        PhantomReference<Object> p = new PhantomReference<>(counter, refQueue);
        counter = null;
        System.gc();
        try {
            Reference<Object> ref = refQueue.remove(1000L);
            if (ref != null) {
                // do something
                System.out.println(ref);
                ref.clear();
            }
        } catch (InterruptedException e) {
            // Handle it
        }
    }
}
