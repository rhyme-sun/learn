package learn.algorithm.practice.p04;

/**
 * 题描述如下：
 * 给定三个字符串 s1、s2、s3，请你帮忙验证 s3 是否是由 s1 和 s2 交错 组成的。
 * <p>
 * 两个字符串 s 和 t 交错的定义与过程如下，其中每个字符串都会被分割成若干非空子字符串：
 * <p>
 * s = s1 + s2 + ... + sn
 * t = t1 + t2 + ... + tm
 * |n - m| <= 1
 * 交错是 s1 + t1 + s2 + t2 + s3 + t3 + ... 或者 t1 + s1 + t2 + s2 + t3 + s3 + ...
 * <p>
 * leetcode: https://leetcode.cn/problems/interleaving-string/
 */
public class Code07_InterleavingString {


    static boolean isInterleave(String s1, String s2, String s3) {
        if (s1 == null || s2 == null || s3 == null) {
            return false;
        }
        if (s1.length() + s2.length() != s3.length()) {
            return false;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        char[] str3 = s3.toCharArray();
        int n = str1.length;
        int m = str2.length;

        // dp[i][j] 表示 s1 的前 i 个字符和 s2 的前 j 个字符是否能够交错组成 s3 的前 i+j 个字符
        boolean[][] dp = new boolean[n + 1][m + 1];
        dp[0][0] = true;
        for (int col = 1; col <= m; col++) {
            if (str2[col - 1] != str3[col - 1]) {
                break;
            }
            dp[0][col] = true;
        }
        for (int row = 1; row <= n; row++) {
            if (str1[row - 1] != str3[row - 1]) {
                break;
            }
            dp[row][0] = true;
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                dp[i][j] = (str1[i - 1] == str3[i + j - 1] && dp[i - 1][j]) ||
                        (str2[j - 1] == str3[i + j - 1] && dp[i][j - 1]);
            }
        }
        return dp[n][m];
    }
}
