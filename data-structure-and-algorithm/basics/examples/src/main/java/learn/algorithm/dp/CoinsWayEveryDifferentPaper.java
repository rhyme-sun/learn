package learn.algorithm.dp;

import learn.algorithm.comparator.ArrayComparator;

/**
 * 从左往右尝试模型
 * 问题描述如下：
 * arr 是货币数组，其中的值都是正数，代表货币的面值。再给定一个正数 aim，表示需要的金额。
 * 每个值都认为是一张货币，即便是值相同的货币也认为每一张都是不同的。从货币数组中选择货币（每个货币只能选择一次），返回组成 aim 的方法数。
 * 例如：arr = [1,1,1]，aim = 2
 * 第 0 个和第 1 个能组成 2，第 1 个和第 2 个能组成 2，第 0 个和第 2 个能组成 2，一共就 3 种方法，所以返回 3。
 */
public class CoinsWayEveryDifferentPaper {

    /**
     * 方法 1，递归尝试
     */
    static int coinWays(int[] arr, int aim) {
        if (aim == 0) {
            return 0;
        }
        return process(arr, 0, aim);
    }

    /**
     * index 位置往后正好 rest 这么多的钱，有几种方法，往前不考虑
     */
    private static int process(int[] arr, int index, int rest) {
        if (rest < 0) {
            // rest <0 一路递归到这里的方法无效，返回 0，不参与方法数计数
            return 0;
        }
        if (index == arr.length) {
            return rest == 0 ? 1 : 0;
        } else {
            return process(arr, index + 1, rest) + process(arr, index + 1, rest - arr[index]);
        }
    }

    /**
     * 动态规划优化
     */
    static int dp(int[] arr, int aim) {
        if (aim == 0) {
            return 0;
        }
        int n = arr.length;
        int[][] dp = new int[n + 1][aim + 1];
        dp[n][0] = 1;
        for (int index = n - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[index][rest] = dp[index + 1][rest] + (rest - arr[index] >= 0 ? dp[index + 1][rest - arr[index]] : 0);
            }
        }
        return dp[0][aim];
    }

    public static void main(String[] args) {
        int maxLen = 20;
        int maxValue = 30;
        int testTime = 1000000;
        for (int i = 0; i < testTime; i++) {
            int[] arr = ArrayComparator.generatePositiveRandomArray(maxLen, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = coinWays(arr, aim);
            int ans2 = dp(arr, aim);
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
