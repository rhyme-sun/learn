package learn.algorithm.coding.practice.p04;

/**
 * 题目描述如下：
 * 求子数组的最大累加和。
 * leetcode: https://leetcode.cn/problems/maximum-subarray/
 */
public class Code02_SubArrayMaxSum {

    static int maxSubArray(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        // 前一个位置最大累加和
        int pre = arr[0];
        int max = pre;
        for (int i = 1; i < arr.length; i++) {
            pre = pre > 0 ? arr[i] + pre : arr[i];
            max = Math.max(max, pre);
        }
        return max;
    }
}
