package learn.algorithm.dp;

public class BobDie {

    /**
     * 求生存概率
     *
     * @param row 起点横坐标
     * @param col 起点横坐标
     * @param k   步数上限
     * @param n   区域纵向范围
     * @param m   区域横向范围
     * @return 生存概率
     */
    static double livePossibility(int row, int col, int k, int n, int m) {
        // 生存概率 = 生存下来的走法 / 总的走法（不考虑范围）
        return (double) process(row, col, k, n, m) / Math.pow(4, k);
    }

    /**
     * 尝试递归
     * 目前在 (row,col) 位置，还有 rest 步要走，走完了如果还在棋盘中就获得 1 次生存走法，返回总的生存点数
     */
    private static long process(int row, int col, int rest, int n, int m) {
        // 超出区域
        if (row < 0 || row == n || col < 0 || col == m) {
            return 0;
        }
        // 还在棋盘中
        if (rest == 0) {
            return 1;
        }
        // 还在棋盘中，还有步数要走
        long up = process(row - 1, col, rest - 1, n, m);
        long down = process(row + 1, col, rest - 1, n, m);
        long left = process(row, col - 1, rest - 1, n, m);
        long right = process(row, col + 1, rest - 1, n, m);
        return up + down + left + right;
    }

    /**
     * 动态规划优化
     */
    private static double dp(int row, int col, int k, int n, int m) {
        long[][][] dp = new long[n][m][k + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                dp[i][j][0] = 1;
            }
        }
        for (int rest = 1; rest <= k; rest++) {
            for (int r = 0; r < n; r++) {
                for (int c = 0; c < m; c++) {
                    dp[r][c][rest] = pick(dp, n, m, r - 1, c, rest - 1);
                    dp[r][c][rest] += pick(dp, n, m, r + 1, c, rest - 1);
                    dp[r][c][rest] += pick(dp, n, m, r, c - 1, rest - 1);
                    dp[r][c][rest] += pick(dp, n, m, r, c + 1, rest - 1);
                }
            }
        }
        return (double) dp[row][col][k] / Math.pow(4, k);
    }

    /**
     * 从 dp 表获取数据，并进行越界判断
     */
    private static long pick(long[][][] dp, int n, int m, int r, int c, int rest) {
        if (r < 0 || r == n || c < 0 || c == m) {
            return 0;
        }
        return dp[r][c][rest];
    }

    public static void main(String[] args) {
        System.out.println(livePossibility(6, 6, 10, 50, 50));
        System.out.println(dp(6, 6, 10, 50, 50));
    }
}
