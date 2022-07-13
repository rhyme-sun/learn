package learn.algorithm.coding.practice.p12;

/**
 * 给你一个字符串 s 和一个字符规律 p，请你来实现一个支持 '.' 和 '*' 的正则表达式匹配。
 * '.' 匹配任意单个字符
 * '*' 匹配零个或多个前面的那一个元素
 * <p>
 * 所谓匹配，是要涵盖 整个 字符串 s 的，而不是部分字符串。
 * <p>
 * 链接：https://leetcode.cn/problems/regular-expression-matching
 */
public class Code04_RegularExpressionMatch {

    static boolean isMatch(String str, String exp) {
        if (str == null || exp == null) {
            return false;
        }
        char[] s = str.toCharArray();
        char[] p = exp.toCharArray();
        return process(s, p, 0, 0);
    }

    // s[i...] 能否被 p[j...] 匹配
    private static boolean process(char[] s, char[] p, int i, int j) {
        if (j == p.length) {
            return i == s.length;
        }
        // p[j+1] 不为 *
        if (j + 1 == p.length || p[j + 1] != '*') {
            return i != s.length && (p[j] == s[i] || p[j] == '.') && process(s, p, i + 1, j + 1);
        }
        // p[j+1] 为 *
//		while (i != s.length && (p[j] == s[i] || p[j] == '.')) {
//			if (process(s, p, i++, j + 2)) {
//				return true;
//			}
//		}
        // 斜率优化
        if (i != s.length && (p[j] == s[i] || p[j] == '.')) {
            return process(s, p, i, j + 2) || process(s, p, i + 1, j);
        }
        return process(s, p, i, j + 2);
    }

    // 改记忆化搜索+斜率优化
    public static boolean isMatch2(String str, String exp) {
        if (str == null || exp == null) {
            return false;
        }
        char[] s = str.toCharArray();
        char[] e = exp.toCharArray();
        if (!isValid(s, e)) {
            return false;
        }
        int[][] dp = new int[s.length + 1][e.length + 1];
        // dp[i][j] = 0, 没算过！
        // dp[i][j] = -1 算过，返回值是false
        // dp[i][j] = 1 算过，返回值是true
        return isValid(s, e) && process2(s, e, 0, 0, dp);
    }

    private static boolean process2(char[] s, char[] e, int si, int ei, int[][] dp) {
        if (dp[si][ei] != 0) {
            return dp[si][ei] == 1;
        }
        boolean ans = false;
        if (ei == e.length) {
            ans = si == s.length;
        } else {
            if (ei + 1 == e.length || e[ei + 1] != '*') {
                ans = si != s.length && (e[ei] == s[si] || e[ei] == '.') && process2(s, e, si + 1, ei + 1, dp);
            } else {
                if (si == s.length) { // ei ei+1 *
                    ans = process2(s, e, si, ei + 2, dp);
                } else { // si没结束
                    if (s[si] != e[ei] && e[ei] != '.') {
                        ans = process2(s, e, si, ei + 2, dp);
                    } else { // s[si] 可以和 e[ei]配上
                        ans = process2(s, e, si, ei + 2, dp) || process2(s, e, si + 1, ei, dp);
                    }
                }
            }
        }
        dp[si][ei] = ans ? 1 : -1;
        return ans;
    }

    // 动态规划版本 + 斜率优化
    public static boolean isMatch3(String str, String pattern) {
        if (str == null || pattern == null) {
            return false;
        }
        char[] s = str.toCharArray();
        char[] p = pattern.toCharArray();
        if (!isValid(s, p)) {
            return false;
        }
        int N = s.length;
        int M = p.length;
        boolean[][] dp = new boolean[N + 1][M + 1];
        dp[N][M] = true;
        for (int j = M - 1; j >= 0; j--) {
            dp[N][j] = (j + 1 < M && p[j + 1] == '*') && dp[N][j + 2];
        }
        // dp[0..N-2][M-1]都等于false，只有dp[N-1][M-1]需要讨论
        if (N > 0 && M > 0) {
            dp[N - 1][M - 1] = (s[N - 1] == p[M - 1] || p[M - 1] == '.');
        }
        for (int i = N - 1; i >= 0; i--) {
            for (int j = M - 2; j >= 0; j--) {
                if (p[j + 1] != '*') {
                    dp[i][j] = ((s[i] == p[j]) || (p[j] == '.')) && dp[i + 1][j + 1];
                } else {
                    if ((s[i] == p[j] || p[j] == '.') && dp[i + 1][j]) {
                        dp[i][j] = true;
                    } else {
                        dp[i][j] = dp[i][j + 2];
                    }
                }
            }
        }
        return dp[0][0];
    }

    // 验证匹配字符串和被匹配字符串是否合法
    private static boolean isValid(char[] s, char[] p) {
        // s 中不能有 '.' or '*'
        for (int i = 0; i < s.length; i++) {
            if (s[i] == '*' || s[i] == '.') {
                return false;
            }
        }
        // 开头的 p[0] 不能是 '*'，没有相邻的 '*'
        for (int i = 0; i < p.length; i++) {
            if (p[i] == '*' && (i == 0 || p[i - 1] == '*')) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String s = "bbbba";
        String p = ".*a*a";
        boolean ans1 = s.matches(p);
        boolean ans = isMatch(s, p);
        System.out.println(ans);
        System.out.println(ans);
    }
}
