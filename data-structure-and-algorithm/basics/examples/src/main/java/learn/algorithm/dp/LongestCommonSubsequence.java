package learn.algorithm.dp;

/**
 * 求两个字符串最长公共子序列的长度
 * leetcode 链接：https://leetcode.com/problems/longest-common-subsequence/
 */
public class LongestCommonSubsequence {

    /**
     * 方法 1，递归尝试
     */
    static int longestCommonSubsequence1(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() == 0 || s2.length() == 0) {
            return 0;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        // 尝试
        return process1(str1, str2, str1.length - 1, str2.length - 1);
    }

    /**
     * 考虑 str1[0~i] 和 str2[0~j] 范围最小公共子序列长度
     *
     * @param str1 str1
     * @param str2 str2
     * @param i    字符串 1 最后考虑的位置
     * @param j    字符串 2 最后考虑的位置
     * @return 最小公共子序列长度
     */
    private static int process1(char[] str1, char[] str2, int i, int j) {
        if (i == 0 && j == 0) {
            return str1[i] == str2[j] ? 1 : 0;
        } else if (i == 0) {
            if (str1[i] == str2[j]) {
                return 1;
            } else {
                return process1(str1, str2, 0, j - 1);
            }
        } else if (j == 0) {
            if (str1[i] == str2[j]) {
                return 1;
            } else {
                return process1(str1, str2, i - 1, 0);
            }
        } else { // i != 0 && j != 0
            // 样本模型（一个样本做行，一个样本做列），使用结尾位置讨论可能性
            // 第一种可能性，公共子序列一定不以 i 位置的字符结尾，可能以 j 位置的字符结尾
            int p1 = process1(str1, str2, i - 1, j);
            // 第二种可能性，公共子序列一定不以 j 位置的字符结尾，可能以 i 位置的字符结尾
            int p2 = process1(str1, str2, i, j - 1);
            // 第三种可能性，公共子序列以 i 和 j 位置的字符结尾
            int p3 = str1[i] == str2[j] ? (1 + process1(str1, str2, i - 1, j - 1)) : 0;
            return Math.max(p1, Math.max(p2, p3));
        }
    }

    /**
     * 动态规划优化
     */
    static int longestCommonSubsequence2(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() == 0 || s2.length() == 0) {
            return 0;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int n = str1.length;
        int m = str2.length;
        int[][] dp = new int[n][m];
        dp[0][0] = str1[0] == str2[0] ? 1 : 0;
        for (int j = 1; j < m; j++) {
            dp[0][j] = str1[0] == str2[j] ? 1 : dp[0][j - 1];
        }
        for (int i = 1; i < n; i++) {
            dp[i][0] = str1[i] == str2[0] ? 1 : dp[i - 1][0];
        }
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                int p1 = dp[i - 1][j];
                int p2 = dp[i][j - 1];
                int p3 = str1[i] == str2[j] ? (1 + dp[i - 1][j - 1]) : 0;
                dp[i][j] = Math.max(p1, Math.max(p2, p3));
            }
        }
        return dp[n - 1][m - 1];
    }

    public static void main(String[] args) {
        String str1 = "a12m3b";
        String str2 = "b12n3a";
        System.out.println(longestCommonSubsequence1(str1, str2));
        System.out.println(longestCommonSubsequence2(str1, str2));
    }
}
