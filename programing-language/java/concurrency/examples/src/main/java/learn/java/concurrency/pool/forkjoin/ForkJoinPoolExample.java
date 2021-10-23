package learn.java.concurrency.pool.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import lombok.extern.slf4j.Slf4j;

/**
 * ForkJoinPoolExample.
 */
@Slf4j
public class ForkJoinPoolExample {

    public static void main(String[] args) {
//        example1();
        example2();
    }

    static void example1() {
        log.info("第 7 项：{}", fibonacci(7));   // expect 13
        log.info("第 10 项：{}", fibonacci(10)); // expect 55
        log.info("第 11 项：{}", fibonacci(11)); // expect 89
    }

    // 项数：1 2 3 4 5 6 7  8  9  10
    // 数值：1 1 2 3 5 8 13 21 34 55
    // 计算公式：F[n]=F[n-1]+F[n-2](n>=1, F[0]=1, F[1]=2)
    static int fibonacci(int n) {
        if (n <= 1) {
            return n;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    static void example2() {
        ForkJoinPool fjp = new ForkJoinPool(4);
        Fibonacci f7 = new Fibonacci(7);
        Fibonacci f10 = new Fibonacci(10);
        Fibonacci f11= new Fibonacci(11);

        log.info("第 7 项：{}", fjp.invoke(f7));   // expect 13
        log.info("第 10 项：{}", fjp.invoke(f10)); // expect 55
        log.info("第 11 项：{}", fjp.invoke(f11)); // expect 89
    }

    static class Fibonacci extends RecursiveTask<Integer> {

        final int n;

        Fibonacci(int n) {
            this.n = n;
        }

        @Override
        protected Integer compute() {
            if (n <= 1) {
                return n;
            }
            Fibonacci f1 = new Fibonacci(n - 1);
            // 创建子任务
            f1.fork();
            Fibonacci f2 = new Fibonacci(n - 2);
            // 等待子任务结果，并合并结果
            return f2.compute() + f1.join();
        }
    }
}