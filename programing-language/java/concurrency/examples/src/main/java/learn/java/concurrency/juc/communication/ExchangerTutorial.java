package learn.java.concurrency.juc.communication;

import java.util.concurrent.Exchanger;

/**
 * {@link Exchanger}
 */
public class ExchangerTutorial {

    static Exchanger<String> exchanger = new Exchanger<>();

    public static void main(String[] args) {
        new Thread(() -> {
            String s = "a";
            try {
                s = exchanger.exchange(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " " + s);

        }, "A").start();


        new Thread(() -> {
            String s = "b";
            try {
                s = exchanger.exchange(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " " + s);
        }, "B").start();
    }
}
