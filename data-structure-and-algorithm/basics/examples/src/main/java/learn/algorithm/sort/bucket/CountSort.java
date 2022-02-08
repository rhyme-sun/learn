package learn.algorithm.sort.bucket;

import learn.algorithm.sort.SortTestUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 计数排序 CountSort
 */
@Slf4j
public class CountSort {

    /**
     * 排序数据的范围为 0~200
     */
    static void countSort(int[] arr) {
        if (arr == null || arr.length == 1) {
            return;
        }
        
        int[] bucket = new int[201];
        for (int i : arr) {
            bucket[i]++;
        }

        int index = 0;
        for (int i = 0; i < bucket.length; i++) {
            int count = bucket[i];
            for (int j = 0; j < count; j++) {
                arr[index++] = i;
            }
        }
    }

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 200;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = SortTestUtils.generateRandomArray2(maxSize, maxValue);
            int[] arr2 = SortTestUtils.copyArray(arr1);
            countSort(arr1);
            SortTestUtils.comparator(arr2);
            if (!SortTestUtils.isEqual(arr1, arr2)) {
                succeed = false;
                SortTestUtils.printArray(arr1);
                SortTestUtils.printArray(arr2);
                break;
            }
        }
        log.info(succeed ? "Nice!" : "Oops!");
        int[] arr = SortTestUtils.generateRandomArray2(maxSize, maxValue);
        SortTestUtils.printArray(arr);
        countSort(arr);
        SortTestUtils.printArray(arr);
    }
}
