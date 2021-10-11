package learn.java.concurrency.juc.atomic;

import java.util.concurrent.atomic.AtomicLong;

/**
 * CASTutorial.
 */
public class CASTutorial {

    volatile int count;

    void addOne() {
        int oldValue;
        int newValue;
        // 自旋
        do {
            oldValue = count;
            newValue = oldValue + 1;
        } while (oldValue != cas(0, oldValue, newValue));
    }

    /**
     * 模拟 CAS
     *
     * @param offset   内存地址，用 long 表示是 64 位操作系统
     * @param expected 预期的值，即更新前的值
     * @param newValue 更新后的值
     * @return CAS 操作后，内存地址的值
     */
    synchronized int cas(long offset, int expected, int newValue) {
        int currentValue = getCurrentValue(offset);
        if (expected == currentValue) {
            setCurrentValue(offset, newValue);
        }
        return currentValue;
    }

    private int getCurrentValue(long offset) {
        // 读取 offset 处的值
        return 0;
    }

    private void setCurrentValue(long offset, int newValue) {
        // 将新的值设置到 offset 处，并返回设置的值
    }
}