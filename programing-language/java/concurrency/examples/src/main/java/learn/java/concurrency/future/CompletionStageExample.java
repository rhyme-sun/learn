package learn.java.concurrency.future;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

/**
 * CompletionStageExample.
 */
@Slf4j
public class CompletionStageExample {

    public static void main(String[] args) {
//        example1();
//        example2();
//        example3();
        example4();
    }

    /**
     * 串行关系
     */
    static void example1() {
        CompletableFuture<String> f0 = CompletableFuture.supplyAsync(() -> "Hello ")
                .thenApply(s -> s + "simon")
                .thenApply(String::toUpperCase);
        log.info(f0.join());
    }

    /**
     * AND 汇聚关系
     */
    static void example2() {
        final CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
            sleep(2, TimeUnit.SECONDS);
            return "Hello";
        });

        final CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
            sleep(3, TimeUnit.SECONDS);
            return "Simon";
        });

        final CompletableFuture<String> f3 = f1.thenCombine(f2, (result1, result2) -> {
            String result = result1 + " " + result2;
            return result.toUpperCase(Locale.ROOT);
        });
        log.info(f3.join());
    }

    private static void sleep(int t, TimeUnit u) {
        try {
            u.sleep(t);
        } catch (InterruptedException e) {
        }
    }

    /**
     * OR 汇聚关系
     */
    static void example3() {
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
            int t = (int) (Math.random() * 5) + 1;
            sleep(t, TimeUnit.SECONDS);
            return "f1-" + t;
        });
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
            int t = (int) (Math.random() * 5) + 1;
            sleep(t, TimeUnit.SECONDS);
            return "f2-" + t;
        });
        CompletableFuture<String> f3 = f1.applyToEither(f2, s -> s);
        log.info(f3.join());
    }

    static void example4() {
        CompletableFuture<Integer> f = CompletableFuture
                .supplyAsync(() -> (7 / 0))
                .thenApply(r -> r * 10)
                .exceptionally(e -> {
                    log.error("Exception", e);
                    return 0;
                });
        log.info(f.join() + "");
    }
}