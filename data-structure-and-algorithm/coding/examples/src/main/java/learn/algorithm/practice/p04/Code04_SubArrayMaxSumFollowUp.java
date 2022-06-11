package learn.algorithm.practice.p04;

/**
 * 题目描述如下：
 * 返回一个数组中，选择的数字不能相邻的情况下，最大子序列累加和。
 * leetcode: https://leetcode.cn/problems/house-robber/
 */
public class Code04_SubArrayMaxSumFollowUp {

    /**
     * 递归尝试一
     */
    static int rob(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        return process(arr, 0, false);
    }

    /**
     * 考虑在 index 位置，求后序最大累加和
     *
     * @param pre 前一个字符是否选过
     */
    private static int process(int[] arr, int index, boolean pre) {
        if (index == arr.length) {
            return 0;
        }
        int max = Integer.MIN_VALUE;
        if (pre) {
            max = Math.max(max, process(arr, index + 1, false));
        } else {
            // 选择当前位置的数
            max = Math.max(max, arr[index] + process(arr, index + 1, true));
            // 不选择当前位置的数
            max = Math.max(max, process(arr, index + 1, false));
        }
        return max;
    }

    /**
     * 递归尝试二
     */
    static int rob2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        return process(arr, 0);
    }

    /**
     * 考虑在 index 位置，求后序最大累加和，可以不用额外的参数表示上个字符是否选过，index 位置决策时，如果 index 位置选了，
     * index + 1 就不能选了
     */
    private static int process(int[] arr, int index) {
        if (index >= arr.length) {
            return 0;
        }
        return Math.max(arr[index] + process(arr, index + 2), process(arr, index + 1));
    }

    /**
     * 递归尝试二记忆化搜索优化
     */
    static int rob3(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int[] dp = new int[arr.length + 2];
        for (int i = 0; i < dp.length; i++) {
            dp[i] = -1;
        }
        return process(arr, 0, dp);
    }

    private static int process(int[] arr, int index, int[] dp) {
        if (dp[index] != -1) {
            return dp[index];
        }
        int ans = 0;
        if (index < arr.length) {
            ans = Math.max(arr[index] + process(arr, index + 2, dp), process(arr, index + 1, dp));
        }
        dp[index] = ans;
        return ans;
    }

    /**
     * 给定一个数组arr，在不能取相邻数的情况下，返回所有组合中的最大累加和
     * 思路：
     * 定义 dp[i] : 表示 arr[0...i] 范围上，在不能取相邻数的情况下，返回所有组合中的最大累加和
     * 在 arr[0...i] 范围上，在不能取相邻数的情况下，得到的最大累加和，可能性分类：
     * 可能性 1) 选出的组合，不包含arr[i]。那么dp[i] = dp[i-1]
     * 比如，arr[0...i] = {3,4,-4}，最大累加和是不包含i位置数的时候
     * <p>
     * 可能性 2) 选出的组合，只包含arr[i]。那么dp[i] = arr[i]
     * 比如，arr[0...i] = {-3,-4,4}，最大累加和是只包含i位置数的时候
     * <p>
     * 可能性 3) 选出的组合，包含arr[i], 且包含arr[0...i-2]范围上的累加和。那么dp[i] = arr[i] + dp[i-2]
     * 比如，arr[0...i] = {3,1,4}，最大累加和是3和4组成的7，因为相邻不能选，所以i-1位置的数要跳过
     * <p>
     * 综上所述：dp[i] = Max { dp[i-1], arr[i] , arr[i] + dp[i-2] }
     */
    static int rob4(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        if (N == 1) {
            return arr[0];
        }
        if (N == 2) {
            return Math.max(arr[0], arr[1]);
        }
        int[] dp = new int[N];
        dp[0] = arr[0];
        dp[1] = Math.max(arr[0], arr[1]);
        for (int i = 2; i < N; i++) {
            dp[i] = Math.max(Math.max(dp[i - 1], arr[i]), arr[i] + dp[i - 2]);
        }
        return dp[N - 1];
    }
}
