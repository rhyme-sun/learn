package learn.algorithm.basics.algorithm.sort.bucket;

import learn.algorithm.comparator.ArrayComparator;

/**
 * 计数排序 CountSort
 */
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
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = ArrayComparator.generateRandomArray(maxSize, maxValue);
            int[] arr2 = ArrayComparator.copyArray(arr1);
            countSort(arr1);
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
