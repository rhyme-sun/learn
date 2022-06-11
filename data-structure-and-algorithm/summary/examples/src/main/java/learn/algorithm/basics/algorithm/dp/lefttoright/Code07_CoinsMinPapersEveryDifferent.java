package learn.algorithm.basics.algorithm.dp.lefttoright;

import learn.algorithm.comparator.ArrayComparator;

/**
 * 题目描述如下：
 * arr 是货币数组，其中的值都是正数。再给定一个正数 aim。每个值都认为是一张货币，返回组成 aim 的最少货币数。
 */
public class Code07_CoinsMinPapersEveryDifferent {

    /**
     * 暴力递归
     */
    static int minCoins(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim <= 0) {
            return 0;
        }
        return process(arr, 0, aim);
    }

    /**
     * 当前来到 arr[index...] 位置，考虑还剩 rest， 返回最小货币数量
     * 使用 Integer.MAX_VALUE 标记无效值
     */
    private static int process(int[] arr, int index, int rest) {
        if (index == arr.length) {
            return rest == 0 ? 0 : Integer.MAX_VALUE;
        }
        // 第一种可能，不选 index 位置的货币
        int p1 = process(arr, index + 1, rest);
        // 第二种可能，选择 index 位置的货币
        int p2 = Integer.MAX_VALUE;
        if (rest - arr[index] >= 0) {
            int next = process(arr, index + 1, rest - arr[index]);
            if (next != Integer.MAX_VALUE) {
                p2 = 1 + next;
            }
        }
        return Math.min(p1, p2);
    }

    /**
     * 动态规划优化，O(arr长度 * aim)
     */
    static int dp1(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim <= 0) {
            return 0;
        }
        int n = arr.length;
        int[][] dp = new int[n + 1][aim + 1];
        dp[n][0] = 0;
        for (int i = 1; i <= aim; i++) {
            dp[n][i] = Integer.MAX_VALUE;
        }
        for (int index = n - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                // 第一种可能，不选 index 位置的货币
                int p1 = dp[index + 1][rest];
                // 第二种可能，选择 index 位置的货币
                int p2 = Integer.MAX_VALUE;
                if (rest - arr[index] >= 0) {
                    int next = dp[index + 1][rest - arr[index]];
                    if (next != Integer.MAX_VALUE) {
                        p2 = 1 + next;
                    }
                }
                dp[index][rest] = Math.min(p1, p2);
            }
        }
        return dp[0][aim];
    }

    public static void main(String[] args) {
        int maxLen = 20;
        int maxValue = 30;
        int testTime = 300000;
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * maxLen);
            int[] arr = ArrayComparator.generatePositiveRandomArray(N, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = minCoins(arr, aim);
            int ans2 = dp1(arr, aim);
            if (ans1 != ans2) {
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
