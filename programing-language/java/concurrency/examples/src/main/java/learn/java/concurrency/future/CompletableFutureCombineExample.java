package learn.java.concurrency.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

/**
 * CompletableFutureCombineExample.
 * Code from: <a href="https://time.geekbang.org/column/article/91569">极客时间《Java并发编程实践》</a>
 */
@Slf4j
public class CompletableFutureCombineExample {

    public static void main(String[] args) {
        thenCombine();
    }

    // 使用 CompletableFuture 模拟泡茶过程
    static void thenCombine() {
        // 任务 1：洗水壶 -> 烧开水
        CompletableFuture<Void> f1 = CompletableFuture.runAsync(() -> {
            log.info("T1: 洗水壶...");
            sleep(1, TimeUnit.SECONDS);

            log.info("T1: 烧开水...");
            sleep(15, TimeUnit.SECONDS);
        });

        // 任务 2：洗茶壶 -> 洗茶杯 -> 拿茶叶
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
            log.info("T2: 洗茶壶...");
            sleep(1, TimeUnit.SECONDS);

            log.info("T2: 洗茶杯...");
            sleep(2, TimeUnit.SECONDS);

            log.info("T2: 拿茶叶...");
            sleep(1, TimeUnit.SECONDS);
            return "龙井";
        });

        // 任务 3：任务 1 和任务 2 完成后执行：泡茶
        CompletableFuture<String> f3 = f1.thenCombine(f2, (__, tf) -> {
            log.info("T1: 拿到茶叶:" + tf);
            log.info("T1: 泡茶...");
            return "上茶: " + tf;
        });

        /*CompletableFuture<String> f3 = f1.thenCompose(s -> f2).thenApply(tf -> {
            log.info("T1: 拿到茶叶:" + tf);
            log.info("T1: 泡茶...");
            return "上茶: " + tf;
        });*/
        // 主线程等待任务 3 执行结果
        log.info(f3.join());
    }

    static void sleep(int t, TimeUnit u) {
        try {
            u.sleep(t);
        } catch (InterruptedException e) {
        }
    }
}