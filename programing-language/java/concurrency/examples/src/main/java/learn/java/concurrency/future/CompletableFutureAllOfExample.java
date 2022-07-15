package learn.java.concurrency.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * CompletableFutureAllOfExample.
 */
public class CompletableFutureAllOfExample {

    public static void main(String[] args) {

    }

    static void example() {
        final CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
            sleep(2, TimeUnit.SECONDS);
            return "Hello";
        });

        final CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
            sleep(3, TimeUnit.SECONDS);
            return "Simon";
        });

        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(f1, f2);
    }

    private static void sleep(int t, TimeUnit u) {
        try {
            u.sleep(t);
        } catch (InterruptedException e) {
        }
    }
}
