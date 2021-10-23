package learn.java.concurrency.future;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletionService;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

/**
 * 小明要做一个询价应用，这个应用需要从三个电商询价，然后保存在自己的数据库里。核心示例代码如下所示，由于是串行的，所以性能很慢，你来试着优化一下。
 * <pre>
 *  r1 = getPriceByS1();
 *  save(r1);
 *  r2 = getPriceByS2();
 *  save(r2);
 *  r3 = getPriceByS3();
 *  save(r3);
 *  </pre>
 */
@Slf4j
public class GetPriceExample {

    public static void main(String[] args) {
//        example1();
//        example2();
//        example3();
//        example4();
        example5();
    }

    static void example1() {
        final int priceFromS1 = getPriceFromS1();
        save(priceFromS1);
        final int priceFromS2 = getPriceFromS2();
        save(priceFromS2);
        final int priceFromS3 = getPriceFromS3();
        save(priceFromS3);
    }

    static void example2() {
        final ExecutorService service = Executors.newFixedThreadPool(3);
        final Future<Integer> submit1 = service.submit(() -> getPriceFromS1());
        final Future<Integer> submit2 = service.submit(() -> getPriceFromS2());
        final Future<Integer> submit3 = service.submit(() -> getPriceFromS3());
        try {
            final Integer price1 = submit1.get();
            save(price1);
            final Integer price2 = submit2.get();
            save(price2);
            final Integer price3 = submit3.get();
            save(price3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            service.shutdown();
        }
    }

    static void example3() {
        final ExecutorService service = Executors.newFixedThreadPool(3);
        final Future<Integer> submit1 = service.submit(() -> getPriceFromS1());
        final Future<Integer> submit2 = service.submit(() -> getPriceFromS2());
        final Future<Integer> submit3 = service.submit(() -> getPriceFromS3());

        final ExecutorService service2 = Executors.newFixedThreadPool(3);
        CountDownLatch latch = new CountDownLatch(3);
        service2.execute(() -> {
            try {
                final Integer price1 = submit1.get();
                save(price1);
                latch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        service2.execute(() -> {
            try {
                final Integer price2 = submit2.get();
                save(price2);
                latch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        service2.execute(() -> {
            try {
                final Integer price3 = submit3.get();
                save(price3);
                latch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            service.shutdown();
            service2.shutdown();
        }
    }

    static void example4() {
        final ExecutorService service = Executors.newFixedThreadPool(3);
        final BlockingQueue<Integer> priceQueue = new LinkedBlockingQueue<>(3);
        service.execute(() -> {
            try {
                priceQueue.put(getPriceFromS1());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        service.execute(() -> {
            try {
                priceQueue.put(getPriceFromS2());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        service.execute(() -> {
            try {
                priceQueue.put(getPriceFromS3());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        for (int i = 0; i < 3; i++) {
            try {
                final Integer price = priceQueue.take();
                service.execute(() -> save(price));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        service.shutdown();
    }

    static void example5() {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        CompletionService<Integer> cs = new ExecutorCompletionService<>(executor);
        cs.submit(() -> getPriceFromS1());
        cs.submit(() -> getPriceFromS2());
        cs.submit(() -> getPriceFromS3());
        for (int i = 0; i < 3; i++) {
            try {
                Integer price = cs.take().get();
                executor.execute(() -> save(price));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
    }

    static int getPriceFromS1() {
        int seconds = (int) (Math.random() * 3) + 1;
        sleep(seconds);
        int price = (int) (Math.random() * 1000);
        log.info("Get price from S1 {}", price);
        return price;
    }

    static int getPriceFromS2() {
        int seconds = (int) (Math.random() * 3) + 1;
        sleep(seconds);
        int price = (int) (Math.random() * 1000);
        log.info("Get price from S2 {}", price);
        return price;
    }

    static int getPriceFromS3() {
        int seconds = (int) (Math.random() * 3) + 1;
        sleep(seconds);
        int price = (int) (Math.random() * 1000);
        log.info("Get price from S3 {}", price);
        return price;
    }

    static void save(int price) {
        log.info("Save price: {}", price);
    }

    static void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
