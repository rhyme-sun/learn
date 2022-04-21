package learn.algorithm.dp;

import learn.algorithm.comparator.ArrayComparator;

/**
 * 问题描述如下：
 * 给定一个正数数组 arr，请把 arr 中所有的数分成两个集合，尽量让两个集合的累加和接近。
 * 返回：最接近的情况下，较小集合的累加和。
 * <p>
 * 上述问题可以换一个描述，比如我们现在有了数组所有元素的累加总和 sum，那么我们求在 arr 种挑选一些元素使其累加和最接近但不超过 sum/2，
 * 此时的累加和就是上述问题所要的答案。
 */
public class SplitSumClosed {

    static int sumClosed(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        return process(arr, 0, sum / 2);
    }

    /**
     * arr[i...] 可以自由选择，请返回累加和尽量接近 rest，但不能超过 rest 的情况下，最近接的累加和
     *
     * @param arr  样本数组
     * @param i    样本位置
     * @param rest 基准值
     * @return 最近接 rest 的累加和（但不超过 rest）
     */
    private static int process(int[] arr, int i, int rest) {
        if (i == arr.length) {
            // 没有数选择，最接近 rest 的值为 0
            return 0;
        } else {
            // 第一种可能性，不选择 i 位置的数
            int p1 = process(arr, i + 1, rest);
            // 第二种可能性，选择 i 位置的数
            int p2 = 0;
            if (arr[i] <= rest) {
                p2 = arr[i] + process(arr, i + 1, rest - arr[i]);
            }
            return Math.max(p1, p2);
        }
    }

    /**
     * 动态规划优化
     */
    private static int dp(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        sum /= 2;
        int n = arr.length;
        int[][] dp = new int[n + 1][sum + 1];
        for (int i = n - 1; i >= 0; i--) {
            for (int rest = 0; rest <= sum; rest++) {
                // 第一种可能性，不选择 i 位置的数
                int p1 = dp[i + 1][rest];
                // 第二种可能性，选择 i 位置的数
                int p2 = 0;
                if (arr[i] <= rest) {
                    p2 = arr[i] + dp[i + 1][rest - arr[i]];
                }
                dp[i][rest] = Math.max(p1, p2);
            }
        }
        return dp[0][sum];
    }

    public static void main(String[] args) {
        int maxLen = 20;
        int maxValue = 50;
        int testTime = 10000;
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * maxLen);
            int[] arr = ArrayComparator.generatePositiveRandomArray(len, maxValue);
            int ans1 = sumClosed(arr);
            int ans2 = dp(arr);
            if (ans1 != ans2) {
                ArrayComparator.printArray(arr);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("Finish!");
    }
}
