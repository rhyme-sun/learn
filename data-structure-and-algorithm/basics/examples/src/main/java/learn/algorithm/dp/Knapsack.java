package learn.algorithm.dp;

/**
 * 背包问题，描述如下：
 * 给定两个长度都为 n 的数组 weights 和 values，weights[i] 和 values[i] 分别代表 i 号物品的重量和价值（物品的重量和价值都非负）。
 * 现有一个载重 bag 的袋子，你装的物品不能超过这个重量，问返回你能装下最多的价值是多少?
 */
public class Knapsack {

    /**
     * 方法 1，尝试递归
     *
     * @param w   货物重量数组
     * @param v   货物价值数组
     * @param bag 背包容量
     * @return 背包能装货物的最大价值
     */
    static int maxValue(int[] w, int[] v, int bag) {
        if (w == null || v == null || w.length != v.length || w.length == 0) {
            return 0;
        }
        return process(w, v, 0, bag);
    }

    /**
     * 从左往右递归，来到 index 号货物
     *
     * @param w     货物重量数组
     * @param v     货物价值数组
     * @param index 第 index 号货物
     * @param rest  背包剩余容量
     * @return 来到 index 位置时，背包能装货物的最大价值
     */
    private static int process(int[] w, int[] v, int index, int rest) {
        if (rest < 0) {
            // -1 表示无效值，上游递归在收到无效值时，不纳入计算结果
            return -1;
        }
        if (index == w.length) {
            return 0;
        }
        // 不拿这个货物
        int p1 = process(w, v, index + 1, rest);
        int p2 = 0;
        int next = process(w, v, index + 1, rest - w[index]);
        if (next != -1) {
            p2 = v[index] + next;
        }
        return Math.max(p1, p2);
    }

    /**
     * 动态规划优化
     */
    static int dp(int[] w, int[] v, int bag) {
        if (w == null || v == null || w.length != v.length || w.length == 0) {
            return 0;
        }
        int n = w.length;
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
        System.out.println(dp(weights, values, bag));
    }
}
