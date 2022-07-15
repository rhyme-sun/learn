package learn.java.concurrency.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import lombok.extern.slf4j.Slf4j;

/**
 * CompletableFutureCreationExample.
 * Code from: <a href="https://time.geekbang.org/column/article/91569">极客时间《Java并发编程实践》</a>
 */
@Slf4j
public class CompletableFutureCreationExample {

    public static void main(String[] args) {
        create1();
        create2();
        create3();
    }

    // 使用 new 关键字创建
    static void create1() {
        try {
            CompletableFuture<String> completableFuture = new CompletableFuture<>();
            completableFuture.complete("Hello!");
            if (completableFuture.isDone()) {
                System.out.println(completableFuture.get());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    // 如果你已经知道计算的结果的话，可以使用静态方法 `completedFuture()` 来创建
    static void create2() {
        try {
            CompletableFuture<String> future = CompletableFuture.completedFuture("Hello!");
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    // 使用静态方法创建
    static void create3() {
        try {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> System.out.println("Hello!"));
            future.get();

            CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "Hello!");
            System.out.println(future2.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}