package learn.java.concurrency.create;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 通过实现 Runnable 接口创建一个线程
 */
@Slf4j
class PrimeCall implements Callable<Integer> {

    int startValue;

    public PrimeCall(int startValue) {
        this.startValue = startValue;
    }

    @Override
    public Integer call() {
        int minPrime = computeMinPrime(startValue);
        log.info("大于{}最小素数为{}", startValue, minPrime);
        return minPrime;
    }

    /**
     * 计算大于给定值的最小素数
     *
     * @param num 指定值
     * @return 大于给定值的最小素数
     */
    private int computeMinPrime(int num) {
        int answer = num + 1;
        while (!isPrime(answer)) {
            answer++;
        }
        return answer;
    }

    private boolean isPrime(int num) {
        if (num < 2) {
            return false;
        }
        for (int i = 2; i * i <= num; i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int startValue = 143;

        PrimeCall primeCall = new PrimeCall(startValue);
        Thread.yield();
        FutureTask<Integer> futureTask = new FutureTask<>(primeCall);
        new Thread(futureTask).start();
        try {
            Integer minPrime = futureTask.get(); // 149
            System.out.println(minPrime);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
