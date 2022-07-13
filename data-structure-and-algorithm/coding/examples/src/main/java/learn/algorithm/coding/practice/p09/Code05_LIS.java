package learn.algorithm.coding.practice.p09;

import learn.algorithm.coding.comparator.ArrayComparator;

/**
 * 给你一个整数数组 nums ，找到其中最长严格递增子序列的长度。
 * <p>
 * leetcode: https://leetcode.cn/problems/longest-increasing-subsequence
 */
public class Code05_LIS {

    /**
     * O(N^2) 的解
     */
    static int lengthOfLIS1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int ans = 1;
        int n = arr.length;
        int[] dp = new int[n];
        for (int i = 0; i < n; i++) {
            int longest = 0;
            // 找到 i 前面比 arr[i] 要小，但对应 dp 表里的值最大
            for (int j = 0; j < i; j++) {
                if (arr[j] < arr[i]) {
                    longest = Math.max(longest, dp[j]);
                }
            }
            dp[i] = longest + 1;
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }

    /**
     * 使用二分优化方法一
     * 时间复杂度为 O(N*logN)
     */
    static int lengthOfLIS2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int n = arr.length;
        int[] ends = new int[n];
        ends[0] = arr[0];

        // 记录 ends 数组末尾位置
        int end = 0;
        for (int i = 1; i < n; i++) {
            // 二分从 ends 里找到小于 arr[i] 的最大值
            int floor = floor(ends, 0, end, arr[i]);
            ends[floor+1] = arr[i];
            end = Math.max(end, floor + 1);
        }
        return end + 1;
    }

    /**
     * 二分从有序数组中找到小于 value 的最大值的位置，找不到返回 -1
     */
    private static int floor(int[] arr, int l, int r, int value) {
        int index = -1;
        while (l <= r) {
            int mid = l + ((r - l) >> 1);
            if (arr[mid] < value) {
                l = mid + 1;
                index = mid;
            } else {
                r = mid - 1;
            }
        }
        return index;
    }

    public static void main(String[] args) {
        int testTimes = 1000;
        int maxValue = 5;
        int maxSize = 10;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = ArrayComparator.generatePositiveRandomArray(maxSize, maxValue);
            int ans1 = lengthOfLIS1(arr);
            int ans2 = lengthOfLIS2(arr);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                ArrayComparator.printArray(arr);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("Finish!");
    }
}