package learn.algorithm.basics.algorithm.dp.range;

/**
 * 求一个字符串的最长回文子序列
 * https://leetcode.cn/problems/longest-palindromic-subsequence/
 */
public class Code02_LongestPalindromeSubsequence {

    static int longestPalindromeSubsequence1(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        return f(str, 0, str.length - 1);
    }

    /**
     * 在 str[l~r] 范围内讨论最长回文子序列长度
     */
    private static int f(char[] str, int l, int r) {
        if (l == r) {
            return 1;
        }
        if (l == r - 1) {
            return str[l] == str[r] ? 2 : 1;
        }
        // 第一种可能性，回文子序列不以 l 位置字符开头，不以 r 位置字符结尾
        int p1 = f(str, l + 1, r - 1);
        // 第二种可能性，回文子序列以 l 位置字符开头，不以 r 位置字符结尾
        int p2 = f(str, l, r - 1);
        // 第三种可能性，回文子序列不以 l 位置字符开头，以 r 位置字符结尾
        int p3 = f(str, l + 1, r);
        // 第三种可能性，回文子序列以 l 位置字符开头，以 r 位置字符结尾
        int p4 = str[l] != str[r] ? 0 : (2 + f(str, l + 1, r - 1));
        return Math.max(Math.max(p1, p2), Math.max(p3, p4));
    }

    static int longestPalindromeSubsequence2(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        int n = str.length;
        int[][] dp = new int[n][n];
        dp[n - 1][n - 1] = 1;
        for (int i = 0; i < n - 1; i++) {
            dp[i][i] = 1;
            dp[i][i + 1] = str[i] == str[i + 1] ? 2 : 1;
        }
        for (int l = n - 3; l >= 0; l--) {
            for (int r = l + 2; r < n; r++) {
                int p1 = dp[l + 1][r - 1];
                int p2 = dp[l][r - 1];
                int p3 = dp[l + 1][r];
                int p4 = str[l] != str[r] ? 0 : (2 + dp[l + 1][r - 1]);
                dp[l][r] = Math.max(Math.max(p1, p2), Math.max(p3, p4));
            }
        }
        return dp[0][n - 1];
    }

    static int longestPalindromeSubsequence3(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        int n = str.length;
        int[][] dp = new int[n][n];
        dp[n - 1][n - 1] = 1;
        for (int i = 0; i < n - 1; i++) {
            dp[i][i] = 1;
            dp[i][i + 1] = str[i] == str[i + 1] ? 2 : 1;
        }
        for (int l = n - 3; l >= 0; l--) {
            for (int r = l + 2; r < n; r++) {
                int p2 = dp[l][r - 1];
                int p3 = dp[l + 1][r];
                dp[l][r] = Math.max(p2, p3);
                if (str[l] == str[r]) {
                    dp[l][r] = Math.max(dp[l][r], 2 + dp[l + 1][r - 1]);
                }
            }
        }
        return dp[0][n - 1];
    }
}
