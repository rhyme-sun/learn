package learn.java.basics.innerclass;

/**
 * AnonymousInnerClassExample.
 */
public class AnonymousInnerClassExample {

    public static void main(String[] args) throws InterruptedException {
        run(1);
        Thread.sleep(1);
    }

    static void run(int a) {
        new Thread() {
            public void run() {
                // Variable 'a' is accessed from within inner class, needs to be final or effectively final
                // a = 2;
                System.out.println(a + 1);
            }
        }.start();
    }
}
