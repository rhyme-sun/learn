package learn.java.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;

/**
 * GuavaRateLimiterExample.
 */
@Slf4j
public class GuavaRateLimiterExample {

    public static void main(String[] args) {
//        limiter1();
        limiter2();
    }

    private static void limiter1() {
        // 限流器流速：2个请求/秒，每隔 500ms 执行一个请求
        RateLimiter limiter = RateLimiter.create(1);
        ExecutorService es = Executors.newFixedThreadPool(1);
        AtomicLong prev = new AtomicLong(System.nanoTime());
        for (int i = 0; i < 20; i++) {
            // 限流器限流
            limiter.acquire();
            // 提交任务异步执行
            es.execute(() -> {
                long cur = System.nanoTime();
                log.info("时间间隔：{}ms", (cur - prev.get()) / 1000_000);
                prev.set(cur);
            });
        }
        es.shutdown();
    }

    private static void limiter2() {
        // 限流器流速：2个请求/秒，每隔 500ms 执行一个请求
        SimpleRateLimiter limiter = SimpleRateLimiter.create(1);
        ExecutorService es = Executors.newFixedThreadPool(1);
        AtomicLong prev = new AtomicLong(System.nanoTime());
        for (int i = 0; i < 20; i++) {
            // 限流器限流
            limiter.acquire();
            // 提交任务异步执行
            es.execute(() -> {
                long cur = System.nanoTime();
                log.info("时间间隔：{}", (cur - prev.get()) / 1000_000);
                prev.set(cur);
            });
        }
        es.shutdown();
    }
}

class SimpleRateLimiter {

    /**
     * 下次令牌可用时间
     */
    long available = System.nanoTime();
    /**
     * 每次间隔时间，单位纳秒，默认 1s
     */
    private long interval = 1000_000_000;

    public static SimpleRateLimiter create(int permitSecond) {
        SimpleRateLimiter limiter = new SimpleRateLimiter();
        limiter.available = permitSecond * 1000_000_000;
        return limiter;
    }

    synchronized long reserve(long now) {
        if (now > available) {
            available = now;
        }
        long at = available;
        available += interval;
        return at;
    }

    void acquire() {
        long now = System.nanoTime();
        final long reserve = reserve(now);
        final long waitNanos = reserve - now;
        if (waitNanos > 0) {
            try {
                TimeUnit.NANOSECONDS.sleep(waitNanos);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}