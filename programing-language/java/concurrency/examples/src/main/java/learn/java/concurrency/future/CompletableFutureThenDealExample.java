package learn.java.concurrency.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * CompletableFutureThenDealExample.
 */
public class CompletableFutureThenDealExample {

    public static void main(String[] args) {
        thenApply();
        thenAccept();
        thenRun();
        whenCompete();

        handleException();
    }

    static void thenApply() {
        try {
            CompletableFuture<String> future = CompletableFuture.completedFuture("Hello ").thenApply(s -> s + "World!");
            System.out.println(future.get());    // Hello World!

            // 忽略本次调用
            future.thenApply(s -> s + "......");
            System.out.println(future.get());    // Hello World!
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    static void thenAccept() {
        CompletableFuture.completedFuture("Hello ").thenApply(s -> s + "World!").thenAccept(System.out::println);
    }

    static void thenRun() {
        CompletableFuture.completedFuture("Hello ").thenApply(s -> s + "World!").thenRun(() -> System.out.println("run"));
    }

    static void whenCompete() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello!")
                .whenComplete((res, ex) -> {
                    // res 代表返回的结果
                    // ex 的类型为 Throwable ，代表抛出的异常
                    System.out.println(res);
                });
    }

    static void handleException() {
        try {
            CompletableFuture<Integer> f = CompletableFuture
                    .supplyAsync(() -> (7 / 0))
                    .thenApply(r -> r * 10)
                    .exceptionally(e -> {
                        e.printStackTrace();
                        return 0;
                    });
            System.out.println(f.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
