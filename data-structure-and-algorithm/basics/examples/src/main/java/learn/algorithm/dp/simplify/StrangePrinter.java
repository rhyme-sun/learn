package learn.algorithm.dp.simplify;

/**
 * 题目描述如下：
 * 有台奇怪的打印机有以下两个特殊要求：
 * <p>
 * 打印机每次只能打印由 同一个字符 组成的序列。
 * 每次可以在从起始到结束的任意位置打印新字符，并且会覆盖掉原来已有的字符。
 * 给你一个字符串 s ，你的任务是计算这个打印机打印它需要的最少打印次数。
 * <p>
 * leetcode: https://leetcode.com/problems/strange-printer/
 */
public class StrangePrinter {

    /**
     * 递归尝试
     */
    static int strangePrinter1(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        return process1(str, 0, str.length - 1);
    }


    /**
     * 考虑要刷出 str[L..R] 的样子，返回最少的转数
     */
    private static int process1(char[] str, int L, int R) {
        if (L == R) {
            return 1;
        }
        // L...R 范围上每次刷一个数字，最多需要刷 R - L + 1
        int ans = R - L + 1;
        for (int k = L + 1; k <= R; k++) {
            // L...k-1 k....R
            ans = Math.min(ans, process1(str, L, k - 1) + process1(str, k, R) - (str[L] == str[k] ? 1 : 0));
        }
        return ans;
    }


    static int strangePrinter2(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        int N = str.length;
        int[][] dp = new int[N][N];
        return process2(str, 0, N - 1, dp);
    }

    private static int process2(char[] str, int L, int R, int[][] dp) {
        if (dp[L][R] != 0) {
            return dp[L][R];
        }
        int ans = R - L + 1;
        if (L == R) {
            ans = 1;
        } else {
            for (int k = L + 1; k <= R; k++) {
                ans = Math.min(ans, process2(str, L, k - 1, dp) + process2(str, k, R, dp) - (str[L] == str[k] ? 1 : 0));
            }
        }
        dp[L][R] = ans;
        return ans;
    }

    static int strangePrinter3(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        int N = str.length;
        int[][] dp = new int[N][N];
        dp[N - 1][N - 1] = 1;
        for (int i = 0; i < N - 1; i++) {
            dp[i][i] = 1;
            dp[i][i + 1] = str[i] == str[i + 1] ? 1 : 2;
        }
        for (int L = N - 3; L >= 0; L--) {
            for (int R = L + 2; R < N; R++) {
                dp[L][R] = R - L + 1;
                for (int k = L + 1; k <= R; k++) {
                    dp[L][R] = Math.min(dp[L][R], dp[L][k - 1] + dp[k][R] - (str[L] == str[k] ? 1 : 0));
                }
            }
        }
        return dp[0][N - 1];
    }
}
