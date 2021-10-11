package learn.java.concurrency.jmm;

/**
 * Sleep
 *
 * @author ykthree
 */
public class Sleep {

    private boolean done;

    public void sleep() {
        while (!this.done) {
            System.out.println("Thread is running.");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Sleep sleep = new Sleep();
        sleep.sleep();
        sleep.done = true;
    }
}