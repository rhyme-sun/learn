package learn.algorithm.sort.quick;

import learn.algorithm.sort.SortTestUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 分区问题
 */
@Slf4j
public class Partition {

    /**
     * 给定一个数组 arr，在 [l,r] 范围内，选定 arr[r] 为分界数，使得小于等于 arr[r] 的数在左边，大于 arr[r] 的数在右边，
     * 最后返回分界位置，步骤如下：
     * 定义分界起始位置（记为 less）为 l-1；
     * 遍历 [l, r-1]，比较 arr[i] 和 arr[r] 的大小；
     * 若 arr[i] <= arr[r]，将 arr[i] 和 less 的下一个位置做交换，并将 less 右移一位；
     * 若 arr[i] > arr[r]，不做处理，比较下个数；
     * 最后将 r 位置的数和 less 的下一个位置交换，并将 less 右移一位，此时的 less 就是最终的分界位置，且分区完毕。
     *
     * @return 分界位置
     */
    static int partition(int[] arr, int l, int r) {
        if (arr == null || l > r) {
            return -1;
        }
        if (l == r) {
            return l;
        }
        int less = l - 1;
        for (int i = l; i < r; i++) {
            if (arr[i] <= arr[r]) {
                SortTestUtils.swap(arr, ++less, i);
            }
        }
        SortTestUtils.swap(arr, ++less, r);
        return less;
    }

    /**
     * for test，仅仅是为了找到边界位置，并没有进行分区，过程如下：
     * 查找数组 [l,r] 范围内小于等于 arr[r] 的个数 less，则 l+less-1 就是小于等于边界位置。
     */
    static int comparator(int[] arr, int l, int r) {
        if (arr == null || l > r) {
            return -1;
        }
        if (l == r) {
            return l;
        }
        int less = 0;
        for (int i = l; i <= r; i++) {
            if (arr[i] <= arr[r]) {
                less++;
            }
        }
        return l + less - 1;
    }

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 10;
        int maxValue = 10;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = SortTestUtils.generateRandomArray(maxSize, maxValue);
            int[] arr2 = SortTestUtils.copyArray(arr1);
            int bounds1 = partition(arr1, 0, arr1.length - 1);
            int bounds2 = comparator(arr2, 0, arr2.length - 1);
            if (bounds1 != bounds2) {
                succeed = false;
                SortTestUtils.printArray(arr1);
                SortTestUtils.printArray(arr2);
                log.info(bounds1);
                log.info(bounds2);
                break;
            }
        }
        log.info(succeed ? "Nice!" : "Oops!");
        int[] arr = SortTestUtils.generateRandomArray(maxSize, maxValue);
        SortTestUtils.printArray(arr);
        int bounds = partition(arr, 0, arr.length - 1);
        SortTestUtils.printArray(arr);
        log.info("{}", bounds);
    }
}
