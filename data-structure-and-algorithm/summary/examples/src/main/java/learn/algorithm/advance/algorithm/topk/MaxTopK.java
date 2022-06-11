package learn.algorithm.advance.algorithm.topk;

import java.util.Arrays;
import java.util.TreeSet;

import learn.algorithm.comparator.ArrayComparator;

/**
 * 问题描述如下：
 * 给定一个无序数组 arr 中，长度为 N，给定一个正数 K，返回 top K 个最大的数。
 */
public class MaxTopK {

    /**
     * 排序+收集，时间复杂度O(N*logN)
     */
    static int[] maxTopK1(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        int N = arr.length;
        k = Math.min(N, k);
        Arrays.sort(arr);
        int[] ans = new int[k];
        for (int i = N - 1, j = 0; j < k; i--, j++) {
            ans[j] = arr[i];
        }
        return ans;
    }

    /**
     * 使用堆，时间复杂度 O(N + K*logN)
     */
    static int[] maxTopK2(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        int n = arr.length;
        k = Math.min(n, k);
        // 从底向上建堆，时间复杂度 O(N)
        for (int i = n - 1; i >= 0; i--) {
            heapify(arr, i, n);
        }
        // 前 K 个数放在 arr 末尾，然后收集， O(K*logN)
        int heapSize = n;
        int count = 0;
        while (heapSize > 0 && count < k) {
            ArrayComparator.swap(arr, 0, --heapSize);
            heapify(arr, 0, heapSize);
            count++;
        }
        int[] ans = new int[k];
        for (int i = n - 1, j = 0; j < k; i--, j++) {
            ans[j] = arr[i];
        }
        return ans;
    }

    /**
     * 从顶向下调整堆（下沉）
     */
    private static void heapify(int[] arr, int index, int heapSize) {
        int left = index * 2 + 1;
        while (left < heapSize) {
            int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
            largest = arr[largest] > arr[index] ? largest : index;
            if (largest == index) {
                break;
            }
            ArrayComparator.swap(arr, largest, index);
            index = largest;
            left = index * 2 + 1;
        }
    }

    /**
     * 方法 3，找到第 K 大的数
     */
    static int[] maxTopK3(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        int n = arr.length;
        k = Math.min(n, k);
        // O(N)
        // FindMinKth.minKth2 为使用 BFPRT 找到无序数组中第 n-k 小的数的值，即第 k 大的数
        int num = FindMinKth.minKth2(arr, n - k);
        int[] ans = new int[k];
        int index = 0;
        for (int i = 0; i < n; i++) {
            if (arr[i] > num) {
                ans[index++] = arr[i];
            }
        }
        for (; index < k; index++) {
            ans[index] = num;
        }
        // O(K*logK)
        Arrays.sort(ans);
        // 逆序
        for (int l = 0, r = k - 1; l < r; l++, r--) {
            ArrayComparator.swap(ans, l, r);
        }
        return ans;
    }

    /**
     * 方法 4，使用有序表，找到第
     *
     * @param arr
     * @param k
     * @return
     */
    static int[] maxTopK4(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        int n = arr.length;
        k = Math.min(n, k);
        TreeSet<Integer> set = new TreeSet<>((o1, o2) -> o1 == o2 ? 1 : -(o1 - o2));
        // N*log(N)
        for (int i : arr) {
            set.add(i);
        }
        int[] ans = new int[k];
        int index = 0;
        // K*log(N)
        for (Integer value : set) {
            if (index == k) {
                break;
            }
            ans[index++] = value;
        }
        return ans;
    }


    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        for (int i = 0; i < testTime; i++) {
            int k = (int) (Math.random() * maxSize) + 1;
            int[] arr = ArrayComparator.generateRandomArray(maxSize, maxValue);

            int[] arr1 = ArrayComparator.copyArray(arr);
            int[] arr2 = ArrayComparator.copyArray(arr);
            int[] arr3 = ArrayComparator.copyArray(arr);
            int[] arr4 = ArrayComparator.copyArray(arr);

            int[] ans1 = maxTopK1(arr1, k);
            int[] ans2 = maxTopK2(arr2, k);
            int[] ans3 = maxTopK3(arr3, k);
            int[] ans4 = maxTopK3(arr4, k);
            if (!ArrayComparator.isEqual(ans1, ans2) || !ArrayComparator.isEqual(ans1, ans3) || !ArrayComparator.isEqual(ans1, ans4)) {
                System.out.println("Oops!");
                ArrayComparator.printArray(arr);
                System.out.println(k);
                ArrayComparator.printArray(ans1);
                ArrayComparator.printArray(ans2);
                ArrayComparator.printArray(ans3);
                ArrayComparator.printArray(ans4);
                break;
            }
        }
        System.out.println("Finish!");
    }
}
