package learn.algorithm.practice.p10;

/**
 * 给定一个布尔表达式和一个期望的布尔结果 result，布尔表达式由 0 (false)、1 (true)、& (AND)、 | (OR) 和 ^ (XOR) 符号组成。
 * 实现一个函数，算出有几种可使该表达式得出 result 值的括号方法。
 * <p>
 * 链接：https://leetcode.cn/problems/boolean-evaluation-lcci
 */
public class Code05_BooleanEvaluation {

    static int countEval(String s, int result) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int n = s.length();
        Info[][] dp = new Info[n][n];
        Info info = process(s.toCharArray(), 0, n - 1, dp);
        return result == 1 ? info.t : info.f;
    }

    /**
     * [l,r] 范围内，任意组合为 true 和 false 的方法数，l 和 r 位置一定为数字，且一共奇数个字符
     */
    private static Info process(char[] express, int l, int r, Info[][] dp) {
        if (dp[l][r] != null) {
            return dp[l][r];
        }
        int t = 0;
        int f = 0;
        if (l == r) {
            t = express[l] == '1' ? 1 : 0;
            f = express[l] == '0' ? 1 : 0;
        } else {
            for (int split = l + 1; split < r; split += 2) {
                Info leftInfo = process(express, l, split - 1, dp);
                Info rightInfo = process(express, split + 1, r, dp);

                int a = leftInfo.t;
                int b = leftInfo.f;
                int c = rightInfo.t;
                int d = rightInfo.f;

                switch (express[split]) {
                    case '&':
                        t += a * c;
                        f += b * c + b * d + a * d;
                        break;
                    case '|':
                        t += a * c + a * d + b * c;
                        f += b * d;
                        break;
                    case '^':
                        t += a * d + b * c;
                        f += a * c + b * d;
                        break;
                }
            }
        }
        dp[l][r] = new Info(t, f);
        return dp[l][r];
    }

    private static class Info {
        int t;
        int f;

        Info(int tr, int fa) {
            t = tr;
            f = fa;
        }
    }

    /**
     * 严格递推
     */
    static int countEval2(String express, int desired) {
        if (express == null || express.equals("")) {
            return 0;
        }
        char[] exp = express.toCharArray();
        int N = exp.length;
        int[][][] dp = new int[2][N][N];
        dp[0][0][0] = exp[0] == '0' ? 1 : 0;
        dp[1][0][0] = dp[0][0][0] ^ 1;
        for (int i = 2; i < exp.length; i += 2) {
            dp[0][i][i] = exp[i] == '1' ? 0 : 1;
            dp[1][i][i] = exp[i] == '0' ? 0 : 1;
            for (int j = i - 2; j >= 0; j -= 2) {
                for (int k = j; k < i; k += 2) {
                    if (exp[k + 1] == '&') {
                        dp[1][j][i] += dp[1][j][k] * dp[1][k + 2][i];
                        dp[0][j][i] += (dp[0][j][k] + dp[1][j][k]) * dp[0][k + 2][i] + dp[0][j][k] * dp[1][k + 2][i];
                    } else if (exp[k + 1] == '|') {
                        dp[1][j][i] += (dp[0][j][k] + dp[1][j][k]) * dp[1][k + 2][i] + dp[1][j][k] * dp[0][k + 2][i];
                        dp[0][j][i] += dp[0][j][k] * dp[0][k + 2][i];
                    } else {
                        dp[1][j][i] += dp[0][j][k] * dp[1][k + 2][i] + dp[1][j][k] * dp[0][k + 2][i];
                        dp[0][j][i] += dp[0][j][k] * dp[0][k + 2][i] + dp[1][j][k] * dp[1][k + 2][i];
                    }
                }
            }
        }
        return dp[desired][0][N - 1];
    }
}
