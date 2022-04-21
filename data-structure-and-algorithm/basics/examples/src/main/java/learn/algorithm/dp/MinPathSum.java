package learn.algorithm.dp;

import learn.algorithm.comparator.MatrixComparator;

/**
 * 问题描述如下：
 * <p>
 * 给定一个二维数组 matrix，一个人必须从左上角出发，最后到达右下角，沿途只可以向下或者向右走，沿途的数字都累加就是距离累加和。
 * 返回最小距离累加和。
 */
public class MinPathSum {

    /**
     * 方法 1，使用 dp 表
     */
    static int minPathSum1(int[][] m) {
        if (m == null || m.length == 0 || m[0] == null || m[0].length == 0) {
            return 0;
        }
        int row = m.length;
        int col = m[0].length;
        int[][] dp = new int[row][col];
        dp[0][0] = m[0][0];
        for (int i = 1; i < row; i++) {
            dp[i][0] = dp[i - 1][0] + m[i][0];
        }
        for (int j = 1; j < col; j++) {
            dp[0][j] = dp[0][j - 1] + m[0][j];
        }
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + m[i][j];
            }
        }
        return dp[row - 1][col - 1];
    }

    /**
     * 方法 2，使用一个一维数组对 dp 进行空间优化
     */
    static int minPathSum2(int[][] m) {
        if (m == null || m.length == 0 || m[0] == null || m[0].length == 0) {
            return 0;
        }
        int row = m.length;
        int col = m[0].length;
        int[] dp = new int[col];
        dp[0] = m[0][0];
        for (int j = 1; j < col; j++) {
            dp[j] = dp[j - 1] + m[0][j];
        }
        for (int i = 1; i < row; i++) {
            dp[0] += m[i][0];
            for (int j = 1; j < col; j++) {
                dp[j] = Math.min(dp[j - 1], dp[j]) + m[i][j];
            }
        }
        return dp[col - 1];
    }

    public static void main(String[] args) {
        int rowSize = 10;
        int colSize = 10;
        int[][] m = MatrixComparator.generateRandomMatrix(rowSize, colSize);
        System.out.println(minPathSum1(m));
        System.out.println(minPathSum2(m));
    }
}
