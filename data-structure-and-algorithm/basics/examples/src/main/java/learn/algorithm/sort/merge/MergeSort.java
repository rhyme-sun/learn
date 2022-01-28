package learn.algorithm.sort.merge;

import learn.algorithm.sort.SortTestUtils;

/**
 * 归并排序，从小到大
 * 让一个数组左边有序，再让一个数组右边有序，最后通过 merge 操作整体有序。
 */
public class MergeSort {

    /**
     * 递归实现
     */
    public static void mergeSort1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process(arr, 0, arr.length - 1);
    }

    static void process(int[] arr, int l, int r) {
        if (r == l) {
            return;
        }
        int m = l + ((r - l) >> 1);
        process(arr, l, m);
        process(arr, m + 1, r);
        merge(arr, l, m, r);
    }

    /**
     * 非递归实现，依次对每 2 个元素（mergeSize=1，一个元素为一组，分为左右两组），每 4 个元素（mergeSize=2），每 8 个元素（mergeSize=4）
     * 每 16 个元素（mergeSize=8）... 进行 merge 操作。直到 mergeSize >= n（左组包含了全部数组元素）。
     */
    public static void mergeSort2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int n = arr.length;
        // 当前有序的左组长度
        int mergeSize = 1;
        while (mergeSize < n) { // log n
            int l = 0;
            while (l < n) {
                // l...m  左组（mergeSize）
                int m = l + mergeSize - 1;
                if (m >= n) {
                    break;
                }
                //  l...m   m+1...r(mergeSize)
                int r = Math.min(m + mergeSize, n - 1);
                merge(arr, l, m, r);
                l = r + 1;
            }
            // 防止越界
            if (mergeSize > n / 2) {
                break;
            }
            mergeSize <<= 1;
        }
    }

    private static void merge(int[] arr, int l, int m, int r) {
        int[] temp = new int[r - l + 1];
        int i = 0;
        int p1 = l;
        int p2 = m + 1;
        while (p1 <= m && p2 <= r) {
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
    }

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = SortTestUtils.generateRandomArray(maxSize, maxValue);
            int[] arr2 = SortTestUtils.copyArray(arr1);
            mergeSort1(arr1);
            mergeSort2(arr2);
            if (!SortTestUtils.isEqual(arr1, arr2)) {
                succeed = false;
                SortTestUtils.printArray(arr1);
                SortTestUtils.printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Oops!");
        int[] arr = SortTestUtils.generateRandomArray(maxSize, maxValue);
        SortTestUtils.printArray(arr);
        mergeSort1(arr);
        SortTestUtils.printArray(arr);
    }
}
