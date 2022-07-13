package learn.java.jvm.reference;

import java.lang.ref.SoftReference;

/**
 * SoftReferenceExample.
 */
public class SoftReferenceExample {

    public static void main(String[] args) {
        SoftReference sr = new SoftReference(new Object());

        if (sr.get() != null) {
            Object prev = sr.get();
        } else {
            Object newObj = new Object();
            sr = new SoftReference(newObj);
        }
    }
}
