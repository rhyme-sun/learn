package learn.spring.tx.stopwatch;

import org.springframework.util.StopWatch;

/**
 * StopWatchExample.
 */
public class StopWatchExample {

    public static void main(String[] args) {
        StopWatch clock = new StopWatch(StopWatchExample.class.getName());
        try {
            clock.start("A");
            Thread.sleep(500);
            clock.stop();
            clock.start("B");
            Thread.sleep(300);
            clock.stop();
            clock.start("C");
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            clock.stop();
            System.out.println(clock.prettyPrint());
        }
    }
}
