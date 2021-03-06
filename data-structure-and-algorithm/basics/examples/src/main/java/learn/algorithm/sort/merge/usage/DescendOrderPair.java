package learn.algorithm.sort.merge.usage;

import learn.algorithm.sort.SortTestUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 数组中某个元素 x，若其右边的元素 y 比 x 小，则存在一个降序对（x,y），求数组降序对总个数。
 * <p>
 * 比如：有这样的一个数组 [1,3,4,2,5]
 * 1 右边的降序对个数：0
 * 3 右边的降序对个数：1
 * 4 右边的降序对个数：1
 * 2 右边的降序对个数：0
 * 5 右边的降序对个数：0
 * 所以数组降序对的个数为：0+1+1+0+0=2
 */
@Slf4j
public class DescendOrderPair {

    /**
     * 使用归并排序求数组小和，时间复杂度为 O(N*logN)
     */
    static int count(int[] arr) {
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
        int[] temp = new int[r - l + 1];
        int i = 0;
        int p1 = l;
        int p2 = m + 1;
        int result = 0;
        while (p1 <= m && p2 <= r) {
            if (arr[p1] > arr[p2]) {
                result += (m - p1 + 1);
            }
            temp[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }

        while (p1 <= m) {
            temp[i++] = arr[p1++];
        }
        while (p2 <= r) {
            temp[i++] = arr[p2++];
        }

        for (int j = 0; j < temp.length; j++) {
            arr[l + j] = temp[j];
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
                if (arr[i] > arr[j]) {
                    result++;
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
//            int[] arr1 = SortTestUtils.generateRandomArray(maxSize, maxValue);
            int[] arr1 = {3, 5, 4};
            int[] arr2 = SortTestUtils.copyArray(arr1);
            int smallSum1 = count(arr1);
            int smallSum2 = comparator(arr2);
            if (smallSum1 != smallSum2) {
                succeed = false;
                SortTestUtils.printArray(arr1);
                SortTestUtils.printArray(arr2);
                break;
            }
        }
        log.info(succeed ? "Nice!" : "Oops!");
        int[] arr = SortTestUtils.generateRandomArray(maxSize, maxValue);
        SortTestUtils.printArray(arr);
        log.info("{}", count(arr));
    }
}
