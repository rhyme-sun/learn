package learn.java.concurrency.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;

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
        final ExecutorService getPriceExecutor = Executors.newFixedThreadPool(3);
        final Future<Long> getPriceTask1 = getPriceExecutor.submit(new GetPriceTask("S1"));
        final Future<Long> getPriceTask2 = getPriceExecutor.submit(new GetPriceTask("S2"));
        final Future<Long> getPriceTask3 = getPriceExecutor.submit(new GetPriceTask("S3"));

        final ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.execute(() -> {
            try {
                final Long aLong = getPriceTask1.get();
                // save
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        executorService.execute(() -> {
            try {
                final Long aLong = getPriceTask2.get();
                // save
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        executorService.execute(() -> {
            try {
                final Long aLong = getPriceTask3.get();
                // save
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
    }


}

@Slf4j
class GetPriceTask implements Callable<Long> {

    private String supplier;

    public GetPriceTask(String supplier) {
        this.supplier = supplier;
    }

    @Override
    public Long call() throws Exception {
        log.info("Get price from {}", supplier);
        return (long) Math.random() * 1000;
    }
}
