package learn.algorithm.coding.practice.p17;

// 测试链接 : https://leetcode-cn.com/problems/21dk04/
public class Code03_DistinctSubseq {

    static int numDistinct(String s, String t) {
        int n = s.length();
        int m = t.length();

        // dp[i][j] 表示用 s 字符串的 0~i 字符组成子串的子序列等于由 t 字符串的 0~j 字符组成子串的个数
        int[][] dp = new int[n][m];
        dp[0][0] = s.charAt(0) == t.charAt(0) ? 1 : 0;
        for (int i = 1; i < n; i++) {
            dp[i][0] = s.charAt(i) == t.charAt(0) ? dp[i - 1][0] + 1 : dp[i - 1][0];
        }
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                dp[i][j] = dp[i - 1][j];
                if (s.charAt(i) == t.charAt(j)) {
                    dp[i][j] += dp[i - 1][j - 1];
                }
            }
        }
        return dp[n - 1][m - 1];
    }

    // 优化边界
    static int numDistinct2(String s, String t) {
        int n = s.length();
        int m = t.length();

        // dp[i][j] 表示用 s 字符串的 0~i 字符组成子串的子序列等于由 t 字符串的 0~j 字符组成子串的个数
        int[][] dp = new int[n][m];
        dp[0][0] = s.charAt(0) == t.charAt(0) ? 1 : 0;
        for (int i = 1; i < n; i++) {
            dp[i][0] = s.charAt(i) == t.charAt(0) ? dp[i - 1][0] + 1 : dp[i - 1][0];
        }
        for (int i = 1; i < n; i++) {
            for (int j = 1; j <= Math.min(m-1, i); j++) {
                dp[i][j] = dp[i - 1][j];
                if (s.charAt(i) == t.charAt(j)) {
                    dp[i][j] += dp[i - 1][j - 1];
                }
            }
        }
        return dp[n - 1][m - 1];
    }

    public static void main(String[] args) {
        String s = "1212311112121132";
        String t = "13";

        System.out.println(numDistinct(s, t));
        System.out.println(numDistinct2(s, t));
    }
}
