package learn.algorithm.basics.algorithm.dp.lefttoright;

import java.util.Arrays;

/**
 * 背包问题
 * 问题描述如下：
 * <p>
 * 给定两个长度都为 *n* 的数组 weights 和 values，`weights[i]` 和 `values[i]` 分别代表 i 号物品的重量和价值（物品的重量和价值都非负）。
 * 现有一个载重 bag 的袋子，你装的物品不能超过这个重量，问返回你能装下最多的价值是多少?
 */
public class Code01_Knapsack {

    // 递归尝试
    static int maxValue(int[] w, int[] v, int bag) {
        if (w == null || v == null || w.length != v.length || w.length == 0) {
            return 0;
        }
        return process(w, v, 0, bag);
    }

    // 当前来到 i 号货物，背包容量还剩 rest，返回从 i 位置开始往后决策最大商品价值
    private static int process(int[] w, int[] v, int i, int rest) {
        if (rest < 0) {
            // -1 表示无效值，上游递归在收到无效值时，不纳入计算结果
            return -1;
        }
        if (i == w.length) {
            return 0;
        }
        // 不拿这个货物
        int p1 = process(w, v, i + 1, rest);
        int p2 = 0;
        int next = process(w, v, i + 1, rest - w[i]);
        if (next != -1) {
            p2 = v[i] + next;
        }
        return Math.max(p1, p2);
    }

    // 记忆化搜索优化
    static int maxValue2(int[] w, int[] v, int bag) {
        if (w == null || v == null || w.length != v.length || w.length == 0) {
            return 0;
        }
        int n = w.length;
        // dp[i][j] 表示从 i 位置，背包容量还剩 j 往后决策，背包能够装下商品的最大价值
        int[][] dp = new int[n + 1][bag + 1];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], -1);
        }
        return process(w, v, 0, bag, dp);
    }

    private static int process(int[] w, int[] v, int i, int rest, int[][] dp) {
        if (rest < 0) {
            return -1;
        }
        if (dp[i][rest] != -1) {
            return dp[i][rest];
        }
        int ans = 0;
        if (i < w.length) {
            // 不拿这个货物
            int p1 = process(w, v, i + 1, rest);
            int p2 = 0;
            int next = process(w, v, i + 1, rest - w[i]);
            if (next != -1) {
                p2 = v[i] + next;
            }
            ans = Math.max(p1, p2);
        }
        dp[i][rest] = ans;
        return ans;
    }

    // 严格递推优化
    static int dp(int[] w, int[] v, int bag) {
        if (w == null || v == null || w.length != v.length || w.length == 0) {
            return 0;
        }
        int n = w.length;
        // dp[i][j] 表示从 i 位置，背包容量还剩 j 往后决策，背包能够装下商品的最大价值
        int[][] dp = new int[n + 1][bag + 1];
        for (int index = n - 1; index >= 0; index--) {
            for (int rest = 0; rest <= bag; rest++) {
                int p1 = dp[index + 1][rest];
                int p2 = 0;
                int next = rest - w[index] < 0 ? -1 : dp[index + 1][rest - w[index]];
                if (next != -1) {
                    p2 = v[index] + next;
                }
                dp[index][rest] = Math.max(p1, p2);
            }
        }
        return dp[0][bag];
    }

    public static void main(String[] args) {
        int[] weights = {3, 2, 4, 7, 3, 1, 7};
        int[] values = {5, 6, 3, 19, 12, 4, 2};
        int bag = 15;
        System.out.println(maxValue(weights, values, bag));
        System.out.println(maxValue2(weights, values, bag));
        System.out.println(dp(weights, values, bag));
    }
}
