package learn.java.concurrency.create;

import lombok.extern.slf4j.Slf4j;

/**
 * 通过实现 Runnable 接口创建一个线程
 */
@Slf4j
class PrimeRun implements Runnable {

    int startValue;

    public PrimeRun(int startValue) {
        this.startValue = startValue;
    }

    @Override
    public void run() {
        int minPrime = computeMinPrime(startValue);
        log.info("大于{}最小素数为{}", startValue, minPrime);
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

        PrimeRun task = new PrimeRun(startValue);
        new Thread(task).start();
    }
}
