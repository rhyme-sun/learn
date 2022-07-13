package learn.java.jvm.gc;

/**
 * FinalizeExample.
 */
public class FinalizeExample {

    public static FinalizeExample SAVE_HOOK = null;

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize..., save this.");
        FinalizeExample.SAVE_HOOK = this;
    }

    public static void main(String[] args) throws InterruptedException {
        SAVE_HOOK = new FinalizeExample();
        SAVE_HOOK = null;
        System.gc();
        Thread.sleep(500);
        if (SAVE_HOOK != null) {
            System.out.println("I'm alive...");
        } else {
            System.out.println("I'm dead...");
        }

        SAVE_HOOK = null;
        Thread.sleep(500);
        if (SAVE_HOOK != null) {
            System.out.println("I'm alive...");
        } else {
            System.out.println("I'm dead...");
        }
    }
}
