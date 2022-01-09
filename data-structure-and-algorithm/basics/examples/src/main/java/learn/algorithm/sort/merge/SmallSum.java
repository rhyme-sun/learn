package learn.algorithm.sort.merge;

import learn.algorithm.sort.SortTestUtils;

/**
 * 求数组小和，数组小和：数组中的一个数左边比它小的数的总和，叫数的小和，所有数的小和累加起来，叫数组小和。
 * 比如：有这样的一个数组 [1,3,4,2,5]
 * 1 左边比 1 小的数：没有
 * 3 左边比 3 小的数：1
 * 4 左边比 4 小的数：1、3
 * 2 左边比 2 小的数：1
 * 5 左边比 5 小的数：1、3、4、 2
 * 所以数组的小和为 1+1+3+1+1+3+4+2=16
 */
public class SmallSum {

    /**
     * 使用归并排序求数组小和，时间复杂度为 O(N*logN)
     */
    static int smallSum(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        return process(arr, 0, arr.length - 1);
    }

    static int process(int[] arr, int l, int r) {
        if (r == l) {
            return 0;
        }
        int m = l + ((r - l) >> 1);
        return process(arr, l, m) + process(arr, m + 1, r) + merge(arr, l, m, r);
    }

    private static int merge(int[] arr, int l, int m, int r) {
        int[] help = new int[r - l + 1];
        int i = 0;
        int p1 = l;
        int p2 = m + 1;
        int result = 0;
        while (p1 <= m && p2 <= r) {
            if (arr[p1] < arr[p2]) {
                result += (r - p2 + 1) * arr[p1];
            }
            help[i++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
        }

        while (p1 <= m) {
            help[i++] = arr[p1++];
        }
        while (p2 <= r) {
            help[i++] = arr[p2++];
        }

        for (int j = 0; j < help.length; j++) {
            arr[l + j] = help[j];
        }
        return result;
    }

    /**
     * 一般方法实现，时间复杂度为 O(N^2)
     */
    static int comparator(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int result = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] < arr[j]) {
                    result += arr[i];
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 10;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = SortTestUtils.generateRandomArray(maxSize, maxValue);
            int[] arr2 = SortTestUtils.copyArray(arr1);
            int smallSum1 = smallSum(arr1);
            int smallSum2 = comparator(arr2);
            if (smallSum1 != smallSum2) {
                succeed = false;
                SortTestUtils.printArray(arr1);
                SortTestUtils.printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Oops!");
        int[] arr = SortTestUtils.generateRandomArray(maxSize, maxValue);
        SortTestUtils.printArray(arr);
        System.out.println(smallSum(arr));
    }
}
