package learn.java.jvm.bytecode;

/**
 * SynchronizedBytecodeExample.
 */
public class SynchronizedBytecodeExample {

    void syncBlock() {
        synchronized (SynchronizedBytecodeExample.class) {
            // ...
        }
    }

    synchronized void syncMethod() {
        // ...
    }

    public static void main(String[] args) {

    }
}
