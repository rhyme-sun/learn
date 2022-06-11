package learn.algorithm.basics.algorithm.dp.range;

import java.util.ArrayList;
import java.util.List;

/**
 * 问题一：一个字符串至少需要添加多少个字符能整体变成回文串；
 * https://leetcode.cn/problems/minimum-insertion-steps-to-make-a-string-palindrome/
 * <p>
 * 问题二：返回问题一的其中一种添加结果；
 * <p>
 * 问题三：返回问题一的所有添加结果。
 */
public class Code03_MinimumInsertionStepsToMakeAStringPalindrome {

    static int minInsertions1(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        return process(s.toCharArray(), 0, s.length() - 1);
    }

    // [l,r] 范围内，讨论让字符成为回文串的最少添加字符数量
    private static int process(char[] str, int l, int r) {
        if (l == r) {
            return 0;
        }
        if (l == r - 1) {
            return str[l] == str[r] ? 0 : 1;
        }
        int p1 = Integer.MAX_VALUE;
        if (str[l] == str[r]) {
            p1 = process(str, l + 1, r - 1);
        }
        int p2 = 1 + process(str, l + 1, r);
        int p3 = 1 + process(str, l, r - 1);
        return Math.min(p1, Math.min(p2, p3));
    }

    static int minInsertions2(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        int n = str.length;
        int[][] dp = new int[n][n];
        for (int i = 0; i < n - 1; i++) {
            dp[i][i + 1] = str[i] == str[i + 1] ? 0 : 1;
        }
        // 从下到上，从左往右
        for (int i = n - 3; i >= 0; i--) {
            for (int j = i + 2; j < n; j++) {
                dp[i][j] = 1 + Math.min(dp[i + 1][j], dp[i][j - 1]);
                if (str[i] == str[j]) {
                    dp[i][j] = Math.min(dp[i][j], dp[i + 1][j - 1]);
                }
            }
        }
        return dp[0][n - 1];
    }

    /**
     * 问题二，使用 dp 表反推
     */
    static String minInsertionsOneWay(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        char[] str = s.toCharArray();
        int n = str.length;
        int[][] dp = new int[n][n];
        for (int i = 0; i < n - 1; i++) {
            dp[i][i + 1] = str[i] == str[i + 1] ? 0 : 1;
        }
        // 从下到上，从左往右
        for (int i = n - 3; i >= 0; i--) {
            for (int j = i + 2; j < n; j++) {
                dp[i][j] = 1 + Math.min(dp[i + 1][j], dp[i][j - 1]);
                if (str[i] == str[j]) {
                    dp[i][j] = Math.min(dp[i][j], dp[i + 1][j - 1]);
                }
            }
        }
        char[] ans = new char[n + dp[0][n - 1]];
        int l = 0, r = n - 1;
        int ansL = 0, ansR = ans.length - 1;
        while (l < r) {
            if (dp[l][r] == dp[l + 1][r] + 1) {
                ans[ansL++] = str[l];
                ans[ansR--] = str[l++];
            } else if (dp[l][r] == dp[l][r - 1]) {
                ans[ansL++] = str[r];
                ans[ansR--] = str[r--];
            } else {
                ans[ansL++] = str[l++];
                ans[ansR--] = str[r--];
            }
        }
        if (l == r) {
            ans[ansL] = str[l];
        }
        return new String(ans);
    }

    /**
     * 本题第三问，返回所有可能的结果
     */
    static List<String> minInsertionsAllWays(String s) {
        List<String> ans = new ArrayList<>();
        if (s == null || s.length() < 2) {
            ans.add(s);
        } else {
            char[] str = s.toCharArray();
            int N = str.length;
            int[][] dp = new int[N][N];
            for (int i = 0; i < N - 1; i++) {
                dp[i][i + 1] = str[i] == str[i + 1] ? 0 : 1;
            }
            for (int i = N - 3; i >= 0; i--) {
                for (int j = i + 2; j < N; j++) {
                    dp[i][j] = Math.min(dp[i][j - 1], dp[i + 1][j]) + 1;
                    if (str[i] == str[j]) {
                        dp[i][j] = Math.min(dp[i][j], dp[i + 1][j - 1]);
                    }
                }
            }
            int M = N + dp[0][N - 1];
            char[] path = new char[M];
            process(str, dp, 0, N - 1, path, 0, M - 1, ans);
        }
        return ans;
    }

    // 当前来到的动态规划中的格子，(L,R)
    // path ....  [pl....pr] ....
    private static void process(char[] str, int[][] dp, int L, int R, char[] path, int pl, int pr, List<String> ans) {
        if (L >= R) { // L > R  L==R
            if (L == R) {
                path[pl] = str[L];
            }
            ans.add(String.valueOf(path));
        } else {
            if (dp[L][R - 1] == dp[L][R] - 1) {
                path[pl] = str[R];
                path[pr] = str[R];
                process(str, dp, L, R - 1, path, pl + 1, pr - 1, ans);
            }
            if (dp[L + 1][R] == dp[L][R] - 1) {
                path[pl] = str[L];
                path[pr] = str[L];
                process(str, dp, L + 1, R, path, pl + 1, pr - 1, ans);
            }
            if (str[L] == str[R] && (L == R - 1 || dp[L + 1][R - 1] == dp[L][R])) {
                path[pl] = str[L];
                path[pr] = str[R];
                process(str, dp, L + 1, R - 1, path, pl + 1, pr - 1, ans);
            }
        }
    }

    public static void main(String[] args) {
        String s = null;
        String ans2 = null;
        List<String> ans3 = null;

        System.out.println("本题第二问，返回其中一种结果测试开始");
        s = "a";
        ans2 = minInsertionsOneWay(s);
        System.out.println(ans2);

        s = "leetcode";
        ans2 = minInsertionsOneWay(s);
        System.out.println(ans2);

        s = "aabaa";
        ans2 = minInsertionsOneWay(s);
        System.out.println(ans2);
        System.out.println("本题第二问，返回其中一种结果测试结束");

        System.out.println();

        System.out.println("本题第三问，返回所有可能的结果测试开始");
        s = "mbadm";
        ans3 = minInsertionsAllWays(s);
        for (String way : ans3) {
            System.out.println(way);
        }
        System.out.println();

        s = "leetcode";
        ans3 = minInsertionsAllWays(s);
        for (String way : ans3) {
            System.out.println(way);
        }
        System.out.println();

        s = "aabaa";
        ans3 = minInsertionsAllWays(s);
        for (String way : ans3) {
            System.out.println(way);
        }
        System.out.println();
        System.out.println("本题第三问，返回所有可能的结果测试结束");
    }
}
