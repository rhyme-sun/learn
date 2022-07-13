package learn.algorithm.coding.practice.p03;

import java.util.Arrays;

/**
 * 题目描述如下：
 * 给你一个整数数组 nums 和一个目标值 goal 。
 * 你需要从 nums 中选出一个子序列，使子序列元素总和最接近 goal 。也就是说，如果子序列元素和为 sum ，你需要最小化绝对差 abs(sum - goal) 。
 * 返回 abs(sum - goal) 可能的最小值 。
 * <p>
 * 1 <= nums.length <= 40
 * -107 <= nums[i] <= 107
 * -109 <= goal <= 109
 * <p>
 * leetcode: https://leetcode.cn/problems/closest-subsequence-sum/
 */
public class Code07_ClosestSubsequenceSum {

    static int minAbsDifference(int[] nums, int goal) {
        if (nums == null || nums.length == 0) {
            return goal;
        }

        int[] l = new int[1 << 20];
        int[] r = new int[1 << 20];

        int le = process(nums, 0, nums.length >> 1, 0, 0, l);
        int re = process(nums, nums.length >> 1, nums.length, 0, 0, r);
        Arrays.sort(l, 0, le);
        Arrays.sort(r, 0, re--);
        int ans = Math.abs(goal);
        for (int i = 0; i < le; i++) {
            int rest = goal - l[i];
            while (re > 0 && Math.abs(rest - r[re - 1]) <= Math.abs(rest - r[re])) {
                re--;
            }
            ans = Math.min(ans, Math.abs(rest - r[re]));
        }
        return ans;
    }

    /**
     * 使用递归求子序列累加和
     * 当前来到 index 位置，考虑要不要选择 index 位置的数参与累加和，返回累加和个数
     */
    private static int process(int[] nums, int index, int end, int sum, int fill, int[] arr) {
        if (index == end) {
            arr[fill++] = sum;
        } else {
            fill = process(nums, index + 1, end, sum, fill, arr);
            fill = process(nums, index + 1, end, sum + nums[index], fill, arr);
        }
        return fill;
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3};
        int goal = 4;

        int ans1 = minAbsDifference(arr, goal);
        System.out.println(ans1);
    }
}
