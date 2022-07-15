package learn.java.concurrency.threadlocal;

/**
 * ThreadLocalMemoryLeakExample.
 * -verbose:gc -Xms20M -Xmx20M
 */
public class ThreadLocalMemoryLeakExample {

    private static ThreadLocal<ContextObject> holder = new ThreadLocal<>();

    static void set(ContextObject object) {
        holder.set(object);
    }

    static ContextObject get() {
        return holder.get();
    }

    public static void main(String[] args) throws Exception {
        holder.set(new ContextObject());
        System.out.println(holder.get());

        // leak, but not oom JDK 11
        final Thread thread = Thread.currentThread();
        while (true) {
            holder = null;
            holder = new ThreadLocal<>();
            holder.set(new ContextObject());
        }
    }

    static class ContextObject {
        private byte[] bytes = new byte[1 * 1024 * 1024];

        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            System.out.println("finalize....");
        }
    }
}
