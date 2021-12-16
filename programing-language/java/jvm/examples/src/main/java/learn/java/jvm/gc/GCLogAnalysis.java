package learn.java.jvm.gc;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.atomic.LongAdder;

import lombok.extern.slf4j.Slf4j;

/**
 * 演示 GC 日志生成与解读，By 铁锚老师。
 */
@SuppressWarnings("ALL")
@Slf4j
public class GCLogAnalysis {

    private static Random random = new Random();

    public static void main(String[] args) {
        // 当前毫秒时间戳
        long startMillis = System.currentTimeMillis();
        // 持续运行毫秒数; 可根据需要进行修改
        long timeoutMillis = Duration.ofSeconds(1).toMillis();
        // 结束时间戳
        long endMillis = startMillis + timeoutMillis;

        LongAdder counter = new LongAdder();
        log.info("开始执行...");
        // 缓存一部分对象，使其不被回收，进入老年代
        int cacheSize = 2000;
        Object[] cachedGarbage = new Object[cacheSize];
        while (System.currentTimeMillis() < endMillis) {
            Object garbage = generateGarbage(100 * 1024);
            counter.increment();
            int randomIndex = random.nextInt(2 * cacheSize);
            if (randomIndex < cacheSize) {
                cachedGarbage[randomIndex] = garbage;
            }
        }
        log.info("执行结束，共生成对象次数：{}", counter.longValue());
    }

    /**
     * 生成垃圾对象
     */
    private static Object generateGarbage(int max) {
        int randomSize = random.nextInt(max);
        int type = randomSize % 4;
        Object result = null;
        switch (type) {
            case 0:
                result = new int[randomSize];
                break;
            case 1:
                result = new byte[randomSize];
                break;
            case 2:
                result = new double[randomSize];
                break;
            default:
                StringBuilder builder = new StringBuilder();
                String randomString = "RandomString";
                while (builder.length() < randomSize) {
                    builder.append(randomString);
                    builder.append(max);
                    builder.append(randomSize);
                }
                result = builder.toString();
                break;
        }
        return result;
    }
}
