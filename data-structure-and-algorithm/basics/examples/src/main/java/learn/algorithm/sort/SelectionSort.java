package learn.algorithm.sort;

import lombok.extern.slf4j.Slf4j;

/**
 * 选择排序（从小到大），过程：
 * 一个长度为 n 的数组，n > 2
 * arr[0~n-1] 范围上，找到最小值，将最小值放到 0 上；
 * arr[1~n-1] 范围上，找到最小值，将最小值放到 1 上；
 * arr[2~n-1] 范围上，找到最小值，将最小值放到 2 上；
 * ...
 * arr[n-2~n-1] 范围上，找到最小值，将最小值放到 n-2 上。
 * 时间复杂度：
 * O(n²)
 */
@Slf4j
public class SelectionSort {

    static void sectionSort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        for (int i = 0; i < array.length - 1; i++) {
            // 记录内部循环最小位置
            int minIndex = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }
            SortTestUtils.swap(array, i, minIndex);
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
            sectionSort(arr1);
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
        sectionSort(arr);
        SortTestUtils.printArray(arr);
    }
}
