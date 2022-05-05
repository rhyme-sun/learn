package learn.algorithm.dp.simplify;

import learn.algorithm.comparator.ArrayComparator;

/**
 * 题目描述如下：
 * 给定一个数组 arr，代表一排有分数的气球。每打爆一个气球都能获得分数，获得分数的规则如下：
 * <p>
 * - 打爆气球获得分数等于该气球的分数乘左边离它最近的没爆气球分数和右边离它最近的没爆气球的分数；
 * - 如果左边没有打爆的气球，就乘 1；
 * - 如果右边没有打爆的气球，就乘 1；
 * <p>
 * 求将气球全部打爆，获得的最高分数。
 * leetcode: https://leetcode.com/problems/burst-balloons/
 */
public class BurstBalloons {

    static int maxCoins(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int[] help = new int[nums.length + 2];
        for (int i = 0; i < nums.length; i++) {
            help[i + 1] = nums[i];
        }
        help[0] = 1;
        help[help.length - 1] = 1;
        return process(help, 1, help.length - 2);
    }

    /**
     * 在 L~R 范围内，假设 L-1 和 R+1 有位置，且气球没有打爆，考虑 L~R 范围内打爆气球的最好分数
     *
     * @return L~R 范围内打爆气球的最好分数
     */
    private static int process(int[] nums, int l, int r) {
        if (l == r) {
            return nums[l - 1] * nums[l] * nums[r + 1];
        }
        // 可能性 1：最后打爆的是 l 位置的气球
        int p1 = process(nums, l + 1, r) + nums[l - 1] * nums[l] * nums[r + 1];
        // 可能性 2：最后打爆的是 r 位置的气球
        int p2 = process(nums, l, r - 1) + nums[l - 1] * nums[r] * nums[r + 1];
        // 可能性 3：最后打爆的是 l~r 范围内的气球
        int p3 = -1;
        for (int i = l + 1; i < r; i++) {
            int left = process(nums, l, i - 1);
            int right = process(nums, i + 1, r);
            int last = nums[l - 1] * nums[i] * nums[r + 1];
            int cur = left + right + last;
            p3 = Math.max(p3, cur);
        }
        return Math.max(Math.max(p1, p2), p3);
    }

    /**
     * 动态规划优化
     */
    static int maxCoins2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int n = nums.length;
        int[] help = new int[n + 2];
        for (int i = 0; i < n; i++) {
            help[i + 1] = nums[i];
        }
        help[0] = 1;
        help[help.length - 1] = 1;
        // dp[i][j] 表示在 i~j 范围内打气球的最大分数
        // 1<=i<=n  1<=j<=n
        int[][] dp = new int[n + 2][n + 2];
        for (int i = 1; i <= n; i++) {
            dp[i][i] = help[i - 1] * help[i] * help[i + 1];
        }
        for (int l = n; l >= 1; l--) {
            for (int r = l + 1; r <= n; r++) {
                int p1 = dp[l + 1][r] + help[l - 1] * help[l] * help[r + 1];
                int p2 = dp[l][r - 1] + help[l - 1] * help[r] * help[r + 1];
                int p3 = -1;
                for (int i = l + 1; i < r; i++) {
                    int left = dp[l][i - 1];
                    int right = dp[i + 1][r];
                    int last = help[l - 1] * help[i] * help[r + 1];
                    int cur = left + right + last;
                    p3 = Math.max(p3, cur);
                }
                dp[l][r] = Math.max(Math.max(p1, p2), p3);
            }
        }
        return dp[1][n];
    }

    public static void main(String[] args) {
        int testTimes = 10000;
        int maxSize = 10;
        int maxValue = 10;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = ArrayComparator.generatePositiveRandomArray(maxSize, maxValue);
            int ans1 = maxCoins(arr);
            int ans2 = maxCoins2(arr);
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
