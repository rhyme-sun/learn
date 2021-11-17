package learn.java.jvm.jmm;

import lombok.extern.slf4j.Slf4j;

/**
 * i = i++;
 * i = ++i;
 */
@Slf4j
public class IPlusPlus {

    public static void main(String[] args) {
        // 8
        testIPlusPlus();
        // 9
        testPlusPlusI();
    }

    static void testIPlusPlus() {
        int i = 8;
        i = i++;
        log.info("{}", i);
    }

    static void testPlusPlusI() {
        int i = 8;
        i = ++i;
        log.info("{}", i);
    }
}
