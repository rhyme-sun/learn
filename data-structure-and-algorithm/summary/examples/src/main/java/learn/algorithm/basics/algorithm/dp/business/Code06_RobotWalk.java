package learn.algorithm.basics.algorithm.dp.business;

/**
 * 机器人走路问题，问题描述如下。
 * 假设有排成一行的 N 个位置，记为 1~N，N 一定大于或等于 2。
 * 开始时机器人在其中的 M 位置上（1<=M<=N），如果机器人来到 1 位置，那么下一步只能往右来到 2 位置；
 * 如果机器人来到 N 位置，那么下一步只能往左来 N-1 位置；
 * 如果机器人来到中间位置，那么下一步可以往左走或者往右走；
 * 规定机器人必须走 K 步，最终能来到 P 位置（1<=P<=N），求机器人从 P 位置走到 K 位置一共有多少走法？
 */
public class Code06_RobotWalk {

    /**
     * 方法 1
     *
     * @param n     位置个数，即数组长度为 n
     * @param start 起始位置（1<=start<=n）
     * @param aim   目标位置（1<=aim<=n）
     * @param steps 可以走的步数
     * @return 走法数量
     */
    static int ways1(int n, int start, int aim, int steps) {
        if (n < 2 || start < 1 || start > n || aim < 1 || aim > n || steps < 1) {
            return -1;
        }
        return process1(n, start, aim, steps);
    }

    /**
     * 当前来到 i 位置，考虑有多少种走法。
     *
     * @param n    位置总数
     * @param i  机器来当前来到的位置
     * @param aim  目标位置
     * @param rest 剩余的步数
     * @return 走法数量
     */
    private static int process1(int n, int i, int aim, int rest) {
        if (rest == 0) {
            return i == aim ? 1 : 0;
        }
        // 走到了 1 位置，只能往右走
        if (i == 1) {
            return process1(n, 2, aim, rest - 1);
        }
        // 走到了 n 位置，只能往左走
        if (i == n) {
            return process1(n, n - 1, aim, rest - 1);
        }
        return process1(n, i - 1, aim, rest - 1) + process1(n, i + 1, aim, rest - 1);
    }

    /**
     * 优化
     */
    static int ways2(int n, int start, int aim, int steps) {
        if (n < 2 || start < 1 || start > n || aim < 1 || aim > n || steps < 1) {
            return -1;
        }
        // dp 就是缓存表，其记录机器人来到 cur 位置，还剩 rest 步的走发数量
        // cur 从 1~n，rest 从 0~steps，所以 int[n + 1][steps + 1] 就可以缓存下所有可能出现的结果
        // dp[cur][rest] == -1 -> process1(cur, rest)之前没算过
        // dp[cur][rest] != -1 -> process1(cur, rest)之前算过
        int[][] dp = new int[n + 1][steps + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= steps; j++) {
                dp[i][j] = -1;
            }
        }
        return process2(n, start, aim, steps, dp);
    }

    /**
     * cur：1~n
     * rest: 0~steps
     */
    private static int process2(int n, int cur, int aim, int rest, int[][] dp) {
        if (dp[cur][rest] != -1) {
            return dp[cur][rest];
        }
        int ans;
        if (rest == 0) {
            ans = cur == aim ? 1 : 0;
        } else if (cur == 1) {
            ans = process2(n, 2, aim, rest - 1, dp);
        } else if (cur == n) {
            ans = process2(n, n - 1, aim, rest - 1, dp);
        } else {
            ans = process2(n, cur - 1, aim, rest - 1, dp) + process2(n, cur + 1, aim, rest - 1, dp);
        }
        dp[cur][rest] = ans;
        return ans;
    }

    /**
     * 最终优化，直接维护 dp 矩阵，得到走法数量
     */
    static int ways3(int n, int start, int aim, int steps) {
        if (n < 2 || start < 1 || start > n || aim < 1 || aim > n || steps < 1) {
            return -1;
        }
        int[][] dp = new int[n + 1][steps + 1];
        // rest = 0
        dp[aim][0] = 1;
        // 遍历列
        for (int rest = 1; rest <= steps; rest++) {
            // cur = 1;
            dp[1][rest] = dp[2][rest - 1];
            // 遍历行
            // 1<cur<n
            for (int cur = 2; cur < n; cur++) {
                dp[cur][rest] = dp[cur - 1][rest - 1] + dp[cur + 1][rest - 1];
            }
            // cur = n
            dp[n][rest] = dp[n - 1][rest - 1];
        }
        return dp[start][steps];
    }

    public static void main(String[] args) {
        System.out.println(ways1(5, 2, 4, 6));
        System.out.println(ways2(5, 2, 4, 6));
        System.out.println(ways3(5, 2, 4, 6));
    }
}
