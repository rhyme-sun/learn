package learn.algorithm.eor;

import lombok.extern.slf4j.Slf4j;

/**
 * 使用异或运算交换两个数的值，不需要额外的空间。
 */
@Slf4j
public class SwapWithOutExtraSpace {

    /**
     * 交换数组 i 和 j 位置的值，不能是同一个位置
     */
    public static void swap(int[] arr, int i, int j) {
        if (i == j) {
            return;
        }
        arr[i] = arr[i] ^ arr[j];
        arr[j] = arr[i] ^ arr[j];
        arr[i] = arr[i] ^ arr[j];
    }

    public static void main(String[] args) {
        int a = 1;
        int b = 1;
        int[] array = {a, b};

        log.info("Before swap.");
        log.info("array[0]={}", array[0]);
        log.info("array[1]={}", array[1]);
        swap(array, 0, 1);
        log.info("After swap.");
        log.info("array[0]={}", array[0]);
        log.info("array[1]={}", array[1]);
    }
}
