package learn.java.concurrency.threadlocal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import lombok.extern.slf4j.Slf4j;

/**
 * ThreadLocalExample.
 */
@Slf4j
public class ThreadLocalExample {

    public static void main(String[] args) {
        try {
            DateFormat df = SafeDateFormat.get();
            log.info("{}", df);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SafeDateFormat.remove();
        }
    }

    static class SafeDateFormat {
        static final ThreadLocal<DateFormat> tl = ThreadLocal.withInitial(() ->
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        static DateFormat get() {
            return tl.get();
        }

        static void remove() {
            tl.remove();
        }
    }
}