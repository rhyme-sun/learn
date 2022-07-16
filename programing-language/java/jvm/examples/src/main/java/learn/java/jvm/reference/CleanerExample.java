package learn.java.jvm.reference;

import java.lang.ref.Cleaner;

/**
 * CleanerExample.
 */
public class CleanerExample implements AutoCloseable {

    private static final Cleaner cleaner = Cleaner.create();
    private final State state;
    private final Cleaner.Cleanable cleanable;

    public CleanerExample() {
        this.state = new State();
        this.cleanable = cleaner.register(this, state);
    }

    @Override
    public void close() {
        cleanable.clean();
    }

    static class State implements Runnable {

        private int numbers;

        State() {
            numbers = Integer.MAX_VALUE;
        }

        @Override
        public void run() {
            // cleanup action accessing State, executed at most once
            System.out.println("clean...");
            numbers = 0;
        }
    }

    public static void main(String[] args) {
        // 主动调用（自动关闭时显式调用）
//        try (CleanerExample example = new CleanerExample()) {
//
//        }

        // GC 调用触发
        CleanerExample example = new CleanerExample();
        example = null;
        System.gc();
    }
}
