package learn.algorithm.basics.algorithm.sort;

import learn.algorithm.comparator.ArrayComparator;

/**
 * 插入排序（从小到大），过程：
 * 一个长度为 n 的数组，n > 2
 * array[0~1] 范围内，array[1] 依次和前面的数比较，小则交换；
 * array[0~2] 范围内，array[2] 依次和前面的数比较，小则交换；
 * array[0~3] 范围内，array[3] 依次和前面的数比较，小则交换；
 * ...
 * array[0~n-1] 范围内，array[n-1] 依次和前面的数比较，小则交换；
 * 时间复杂度：
 * O(n²)
 */
public class InsertionSort {

    static void insertionSort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        for (int i = 1; i < array.length; i++) {
            for (int j = i; j > 0; j--) {
                if (array[j - 1] > array[j]) {
                    ArrayComparator.swap(array, j - 1, j);
                }
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
            insertionSort(arr1);
            ArrayComparator.comparator(arr2);
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
