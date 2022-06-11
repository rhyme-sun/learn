package learn.algorithm.basics.algorithm.sort;

import learn.algorithm.comparator.ArrayComparator;

/**
 * 冒泡排序（从小到大），过程：
 * 一个长度为 n 的数组，n > 2
 * arr[0~n-1] 范围上，依次比较相邻的两个数，前一个位置比后一个位置大，则交换；
 * arr[0~n-2] 范围上，依次比较相邻的两个数，前一个位置比后一个位置大，则交换；
 * arr[0~n-3] 范围上，依次比较相邻的两个数，前一个位置比后一个位置大，则交换；
 * ...
 * arr[0-1] 范围上，依次比较相邻的两个数，前一个位置比后一个位置大，则交换。
 * 时间复杂度：
 *
 */
public class BubbleSort {

    static void bubbleSort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        for (int i = array.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (array[j] > array[j + 1]) {
                    ArrayComparator.swap(array, j, j + 1);
                }
            }
        }
    }

    static void bubbleSort2(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        for (int i = array.length - 1; i > 0; i--) {
            boolean swap = false;
            for (int j = 0; j < i; j++) {
                if (array[j] > array[j + 1]) {
                    ArrayComparator.swap(array, j, j + 1);
                    swap = true;
                }
            }
            if (!swap) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = ArrayComparator.generateRandomArray(maxSize, maxValue);
            int[] arr2 = ArrayComparator.copyArray(arr1);
            bubbleSort(arr1);
            bubbleSort2(arr2);
            if (!ArrayComparator.isEqual(arr1, arr2)) {
                System.out.println("Oops!");
                ArrayComparator.printArray(arr1);
                ArrayComparator.printArray(arr2);
                break;
            }
        }
        System.out.println("Finish!");
    }
}
