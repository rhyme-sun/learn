package learn.java.concurrency.create;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 使用线程池运行一个线程
 */
public class PrimeThreadPool {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();

        int startValue = 146;
        PrimeRun runnableTask = new PrimeRun(startValue);
        executorService.execute(runnableTask);

        PrimeCall callableTask = new PrimeCall(startValue);
        Future<Integer> future = executorService.submit(callableTask);
        Integer result = future.get();
        System.out.println(result);

        executorService.shutdown();
    }
}
