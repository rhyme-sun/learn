package learn.java.concurrency.juc.atomic.cas;

/**
 * CAS.
 */
public class CAS {

    volatile int count;

    void plus() {
        int compare_value;
        int exchange_value;
        // 自旋
        do {
            compare_value = count;
            exchange_value = compare_value + 1;
        } while (compare_value != cas(0, compare_value, exchange_value));
    }


    /**
     * 模拟 CAS
     *
     * @param dest           内存地址，用 long 表示是 64 位操作系统
     * @param compare_value  旧值
     * @param exchange_value 新值
     * @return 更新成功返回 compare_value，否则返回 exchange_value
     */
    synchronized int cas(long dest, int compare_value, int exchange_value) {
        if (getCurrentValue(dest) == compare_value) {
            setCurrentValue(dest, exchange_value);
            return compare_value;
        } else {
            return exchange_value;
        }
    }

    private int getCurrentValue(long dest) {
        return 0;
    }

    private void setCurrentValue(long dest, int exchange_value) {
    }
}
