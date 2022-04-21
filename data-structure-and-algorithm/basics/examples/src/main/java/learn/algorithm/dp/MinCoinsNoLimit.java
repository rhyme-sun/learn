package learn.algorithm.dp;

import learn.algorithm.comparator.ArrayComparator;

/**
 * 题目描述如下：
 * arr 是面值数组，其中的值都是正数且没有重复。再给定一个正数 aim。每个值都认为是一种面值，且认为张数是无限的。返回组成 aim 的最少货币数量。
 * 比如有 arr=[1,2,5,10]，aim=1000，那么组成 aim 需要最少货币张数为 100（都选 10 面值的货币
 */
public class MinCoinsNoLimit {

    /**
     * 递归尝试
     */
    static int minCoins(int[] arr, int aim) {
        if (aim == 0) {
            return 0;
        }
        return process(arr, 0, aim);
    }

    /**
     * 考虑 index 及之后的面值，每种面值张数自由选择，还剩 rest，返回最小张数
     * 使用 Integer.MAX_VALUE 标记无效值
     */
    public static int process(int[] arr, int index, int rest) {
        if (index == arr.length) {
            return rest == 0 ? 0 : Integer.MAX_VALUE;
        } else {
            int ans = Integer.MAX_VALUE;
            for (int zhang = 0; zhang * arr[index] <= rest; zhang++) {
                int next = process(arr, index + 1, rest - zhang * arr[index]);
                if (next != Integer.MAX_VALUE) {
                    ans = Math.min(ans, zhang + next);
                }
            }
            return ans;
        }
    }

    /**
     * 动态递归优化
     */
    static int dp1(int[] arr, int aim) {
        if (aim == 0) {
            return 0;
        }
        int n = arr.length;
        int[][] dp = new int[n + 1][aim + 1];
        dp[n][0] = 0;
        for (int j = 1; j <= aim; j++) {
            dp[n][j] = Integer.MAX_VALUE;
        }
        for (int index = n - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                int ans = Integer.MAX_VALUE;
                for (int zhang = 0; zhang * arr[index] <= rest; zhang++) {
                    int next = dp[index + 1][rest - zhang * arr[index]];
                    if (next != Integer.MAX_VALUE) {
                        ans = Math.min(ans, zhang + next);
                    }
                }
                dp[index][rest] = ans;
            }
        }
        return dp[0][aim];
    }

    /**
     * 去除迭代行为
     */
    static int dp2(int[] arr, int aim) {
        if (aim == 0) {
            return 0;
        }
        int n = arr.length;
        int[][] dp = new int[n + 1][aim + 1];
        dp[n][0] = 0;
        for (int j = 1; j <= aim; j++) {
            dp[n][j] = Integer.MAX_VALUE;
        }
        for (int index = n - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[index][rest] = dp[index + 1][rest];
                // 保证 dp[index][rest - arr[index]] 位置有值，且值是有效
                if (rest - arr[index] >= 0 && dp[index][rest - arr[index]] != Integer.MAX_VALUE) {
                    // 这个规律由 dp 表观察分析所得
                    dp[index][rest] = Math.min(dp[index + 1][rest], dp[index][rest - arr[index]] + 1);
                }
            }
        }
        return dp[0][aim];
    }

    public static void main(String[] args) {
        int maxLen = 20;
        int maxValue = 30;
        int testTime = 300000;
        for (int i = 0; i < testTime; i++) {
            int n = (int) (Math.random() * maxLen);
            int[] arr = ArrayComparator.generatePositiveRandomArray(n, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = minCoins(arr, aim);
            int ans2 = dp1(arr, aim);
            int ans3 = dp2(arr, aim);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("Oops!");
                ArrayComparator.printArray(arr);
                System.out.println(aim);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("Finish!");
    }
}
