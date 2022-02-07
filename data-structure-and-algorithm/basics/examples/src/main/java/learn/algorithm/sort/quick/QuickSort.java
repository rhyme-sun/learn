package learn.algorithm.sort.quick;

import learn.algorithm.sort.SortTestUtils;

/**
 * 随机快排
 */
public class QuickSort {

    static void quickSort1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process1(arr, 0, arr.length - 1);
    }

    private static void process1(int[] arr, int l, int r) {
        if (l >= r) {
            return;
        }
        // l..r partition arr[r]  [<=arr[r]   arr[r]    >arr[r]]
        int m = Partition.partition(arr, l, r);
        process1(arr, l, m - 1);
        process1(arr, m + 1, r);
    }

    static void quickSort2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process2(arr, 0, arr.length - 1);
    }

    private static void process2(int[] arr, int l, int r) {
        if (l >= r) {
            return;
        }
        int[] equalArea = NetherlandsFlag.netherLandsFlag(arr, l, r);
        process2(arr, l, equalArea[0] - 1);
        process2(arr, equalArea[1] + 1, r);
    }

    static void quickSort3(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process3(arr, 0, arr.length - 1);
    }

    private static void process3(int[] arr, int l, int r) {
        if (l >= r) {
            return;
        }
        SortTestUtils.swap(arr, l + (int) (Math.random() * (r - l + 1)), r);
        int[] equalArea = NetherlandsFlag.netherLandsFlag(arr, l, r);
        process3(arr, l, equalArea[0] - 1);
        process3(arr, equalArea[1] + 1, r);
    }

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = SortTestUtils.generateRandomArray(maxSize, maxValue);
            int[] arr2 = SortTestUtils.copyArray(arr1);
            int[] arr3 = SortTestUtils.copyArray(arr1);
            quickSort1(arr1);
            quickSort2(arr2);
            quickSort3(arr3);
            if (!SortTestUtils.isEqual(arr1, arr2) || !SortTestUtils.isEqual(arr2, arr3)) {
                succeed = false;
                SortTestUtils.printArray(arr1);
                SortTestUtils.printArray(arr2);
                SortTestUtils.printArray(arr3);
                break;
            }
        }
        log.info(succeed ? "Nice!" : "Oops!");
        int[] arr = SortTestUtils.generateRandomArray(maxSize, maxValue);
        SortTestUtils.printArray(arr);
        int[] arrA = SortTestUtils.copyArray(arr);
        int[] arrB = SortTestUtils.copyArray(arr);
        int[] arrC = SortTestUtils.copyArray(arr);
        quickSort1(arrA);
        quickSort1(arrB);
        quickSort1(arrC);
        SortTestUtils.printArray(arrA);
        SortTestUtils.printArray(arrB);
        SortTestUtils.printArray(arrC);
    }
}
