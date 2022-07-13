package learn.algorithm.coding.practice.p01;

import java.util.HashMap;
import java.util.Map;

import learn.algorithm.coding.comparator.ArrayComparator;

/**
 * 题目描述如下：
 * 给定一个数组 arr，你可以在每个数字之前决定加或者减，但是必须所有数字都参与，再给定一个数 target，请问最后算出 target 的方法数是多少？
 */
public class Code07_TargetSum {

    static int targetSum1(int[] arr, int target) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        return process(arr, 0, target);
    }

    /**
     * 考虑当前来到 i 位置，距离 target 还剩 rest，返回凑成 target 的方法数量
     *
     * @return 凑成 target 的方法数量
     */
    private static int process(int[] arr, int i, int rest) {
        if (i == arr.length) {
            return rest == 0 ? 1 : 0;
        }
        // 可能性 1：i 位置的数选择加
        int p1 = process(arr, i + 1, rest - arr[i]);
        // 可能性 2：i 位置的数选择减
        int p2 = process(arr, i + 1, rest + arr[i]);
        return p1 + p2;
    }

    /**x
     * 记忆化搜索优化
     */
    static int targetSum2(int[] arr, int target) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        Map<String, Integer> dp = new HashMap<>();
        return process(arr, 0, target, dp);
    }

    /**
     * 考虑当前来到 i 位置，距离 target 还剩 rest，返回凑成 target 的方法数量
     *
     * @return 凑成 target 的方法数量
     */
    private static int process(int[] arr, int i, int rest, Map<String, Integer> dp) {
        String key = i + "_" + rest;
        Integer res = dp.get(key);
        if (res != null) {
            return res;
        }
        if (i == arr.length) {
            return rest == 0 ? 1 : 0;
        }
        // 可能性 1：i 位置的数选择加
        int p1 = process(arr, i + 1, rest - arr[i]);
        // 可能性 2：i 位置的数选择减
        int p2 = process(arr, i + 1, rest + arr[i]);
        res = p1 + p2;
        dp.put(key, res);
        return res;
    }

    /**
     * 进一步优化成背包问题
     */
    static int targetSum3(int[] arr, int target) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int[] positiveArr = new int[arr.length];
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            int value = arr[i];
            if (value < 0) {
                value = -value;
            }
            positiveArr[i] = value;
            sum += value;
        }

        if (sum < target) {
            return 0;
        }
        if ((target & 1) != (sum & 1)) {
            return 0;
        }
        int k = (sum + target) / 2;
        return process3(positiveArr, 0, k);
    }

    /**
     * 背包问题，考虑当前来到 i 为位置，背包剩余容量为 rest，求恰好填满背包的方法数。
     */
    private static int process3(int[] arr, int i, int rest) {
        if (rest < 0) {
            return 0;
        }
        if (i == arr.length) {
            return rest == 0 ? 1 : 0;
        }
        // 可能性 1：不选择 i 位置的货物
        int p1 = process3(arr, i + 1, rest);
        // 可能性 2：选择 i 位置的货物
        int p2 = process3(arr, i + 1, rest - arr[i]);
        return p1 + p2;
    }

    /**
     * 基于背包问题的严格递推优化
     */
    static int targetSum4(int[] arr, int target) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int n = arr.length;
        int[] positiveArr = new int[n];
        int sum = 0;
        for (int i = 0; i < n; i++) {
            int value = arr[i];
            if (value < 0) {
                value = -value;
            }
            positiveArr[i] = value;
            sum += value;
        }

        if (sum < target) {
            return 0;
        }
        if ((target & 1) != (sum & 1)) {
            return 0;
        }
        int k = (sum + target) / 2;

        // dp[i][j] 表示来到数组 i 位置，还剩 j 时的方法数
        // 0<=i<=n  0<=j<=k
        int[][] dp = new int[n + 1][k + 1];
        dp[n][0] = 1;

        // 从下往上，从右往左构建
        for (int i = n - 1; i >= 0; i--) {
            for (int j = k; j >= 0; j--) {
                dp[i][j] = dp[i + 1][j];
                if (j - positiveArr[i] >= 0) {
                    dp[i][j] += dp[i + 1][j - positiveArr[i]];
                }
            }
        }
        return dp[0][k];
    }

    /**
     * 基于背包问题的严格递推优化，空间压缩
     */
    static int targetSum5(int[] arr, int target) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int n = arr.length;
        int[] positiveArr = new int[n];
        int sum = 0;
        for (int i = 0; i < n; i++) {
            int value = arr[i];
            if (value < 0) {
                value = -value;
            }
            positiveArr[i] = value;
            sum += value;
        }

        if (sum < target) {
            return 0;
        }
        if ((target & 1) != (sum & 1)) {
            return 0;
        }
        int k = (sum + target) / 2;

        // dp[j] 表示 i 行 j 列元素，i 用变量表示
        int[] dp = new int[k + 1];
        dp[0] = 1;

        // 从下往上，从右往左构建
        for (int i = n - 1; i >= 0; i--) {
            for (int j = k; j >= 0; j--) {
                if (j - positiveArr[i] >= 0) {
                    dp[j] += dp[j - positiveArr[i]];
                }
            }
        }
        return dp[k];
    }

    public static void main(String[] args) {
        int testTimes = 10000;
        int maxSize = 20;
        int maxValue = 10;
        int target = 0;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = ArrayComparator.generateRandomArray(maxSize, maxValue);
            int ans1 = targetSum1(arr, target);
            int ans2 = targetSum2(arr, target);
            int ans3 = targetSum3(arr, target);
            int ans4 = targetSum4(arr, target);
            int ans5 = targetSum5(arr, target);
            if (ans1 != ans2 || ans1 != ans3 || ans1 != ans4 || ans1 != ans5) {
                System.out.println("Oops!");
                ArrayComparator.printArray(arr);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                System.out.println(ans4);
                System.out.println(ans5);
                break;
            }
        }
        System.out.println("Finish!");
    }
}
