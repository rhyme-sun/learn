package learn.algorithm.sort;

import lombok.extern.slf4j.Slf4j;

/**
 * 冒泡排序（从小到大），过程：
 * 一个长度为 n 的数组，n > 2
 * arr[0~n-1] 范围上，依次比较相邻的两个数，前一个位置比后一个位置大，则交换；
 * arr[0~n-2] 范围上，依次比较相邻的两个数，前一个位置比后一个位置大，则交换；
 * arr[0~n-3] 范围上，依次比较相邻的两个数，前一个位置比后一个位置大，则交换；
 * ...
 * arr[0-1] 范围上，依次比较相邻的两个数，前一个位置比后一个位置大，则交换。
 * 时间复杂度：
 * O(n²)
 */
@Slf4j
public class BubbleSort {

    static void bubbleSort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        for (int i = array.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (array[j] > array[j + 1]) {
                    SortTestUtils.swap(array, j, j + 1);
                }
            }
        }
    }

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = SortTestUtils.generateRandomArray(maxSize, maxValue);
            int[] arr2 = SortTestUtils.copyArray(arr1);
            bubbleSort(arr1);
            SortTestUtils.comparator(arr2);
            if (!SortTestUtils.isEqual(arr1, arr2)) {
                succeed = false;
                SortTestUtils.printArray(arr1);
                SortTestUtils.printArray(arr2);
                break;
            }
        }
        log.info(succeed ? "Nice!" : "Oops!");
        int[] arr = SortTestUtils.generateRandomArray(maxSize, maxValue);
        SortTestUtils.printArray(arr);
        bubbleSort(arr);
        SortTestUtils.printArray(arr);
    }
}
