package learn.algorithm.coding.practice.p01;

import java.util.Arrays;

import learn.algorithm.coding.comparator.ArrayComparator;

/**
 * 题目描述如下：
 * 给定一个有序数组 arr，代表坐落在 x 轴上的点，给定一个正数 k，代表绳子的长度，返回绳子最多压中几个点？即使绳子边缘处盖住点也算盖住。
 */
public class Code01_CordCoverMaxPoint {

    /**
     * 暴力解：
     * 依次考虑数组上每个位置右侧离其最近且距离不超过 k 的位置，统计个数，得到最优解
     * 时间复杂度 O(N*logN)
     */
    static int maxPoint1(int[] arr, int k) {
        if (arr == null || arr.length == 0 || k <= 0) {
            return 0;
        }
        int res = 1;
        for (int i = 0; i < arr.length; i++) {
            int nearest = nearestIndex(arr, i, arr[i] - k);
            res = Math.max(res, i - nearest + 1);
        }
        return res;
    }

    private static int nearestIndex(int[] arr, int R, int value) {
        int L = 0;
        int index = R;
        while (L <= R) {
            int mid = L + ((R - L) >> 1);
            if (arr[mid] >= value) {
                index = mid;
                R = mid - 1;
            } else {
                L = mid + 1;
            }
        }
        return index;
    }

    /**
     * 使用滑动窗口
     */
    static int maxPoint2(int[] arr, int k) {
        if (arr == null || arr.length == 0 || k <= 0) {
            return 0;
        }
        int n = arr.length;
        int l = 0, r = 0;
        int max = 1;
        while (r < n) {
            if (arr[r] - arr[l] <= k) {
                max = Math.max(max, r - l + 1);
                r++;
            } else {
                l++;
            }
        }
        return max;
    }

    public static void main(String[] args) {
        int testTimes = 10000;
        int maxSize = 100;
        int maxValue = 1000;

        for (int i = 0; i < testTimes; i++) {
            int[] arr = ArrayComparator.generateRandomArray(maxSize, maxValue);
            Arrays.sort(arr);
            int k = (int) (Math.random() * maxValue) + 1;

            int ans1 = maxPoint1(arr, k);
            int ans2 = maxPoint2(arr, k);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                ArrayComparator.printArray(arr);
                System.out.println(k);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("Finish!");
    }
}
