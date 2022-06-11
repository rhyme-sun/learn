package learn.algorithm.coding.dp;

/**
 * 题目描述如下：
 * 给定一个二维数组 matrix，你可以从任何位置出发，走向上下左右四个方向，返回能走出来的最长的递增链长度。
 */
public class LongestIncreasingPath {

    static int longestPath(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return 0;
        }
        int row = matrix.length;
        int col = matrix[0].length;
        int longestPath = 1;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                longestPath = Math.max(longestPath, process(matrix, i, j));
            }
        }
        return longestPath;
    }

    /**
     * 考虑当前来到 (i,j) 位置，能够走出的最长路径
     *
     * @return 能够走出的最长路径
     */
    private static int process(int[][] matrix, int i, int j) {
        int row = matrix.length;
        int col = matrix[0].length;
        int longestPath = 1;
        // 可能性 1，向左边走
        if (j > 0 && matrix[i][j] < matrix[i][j - 1]) {
            longestPath = Math.max(longestPath, 1 + process(matrix, i, j - 1));
        }
        // 可能性 2，向右边走
        if (j < col - 1 && matrix[i][j] < matrix[i][j + 1]) {
            longestPath = Math.max(longestPath, 1 + process(matrix, i, j + 1));
        }
        // 可能性 3，向上边走
        if (i > 0 && matrix[i][j] < matrix[i - 1][j]) {
            longestPath = Math.max(longestPath, 1 + process(matrix, i - 1, j));
        }
        // 可能性 4，向下边走
        if (i < row - 1 && matrix[i][j] < matrix[i + 1][j]) {
            longestPath = Math.max(longestPath, 1 + process(matrix, i + 1, j));
        }
        return longestPath;
    }

    /**
     * 记忆化搜索优化
     */
    static int longestPath2(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return 0;
        }
        int row = matrix.length;
        int col = matrix[0].length;
        int[][] dp = new int[row][col];
        int longestPath = 1;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                longestPath = Math.max(longestPath, process(matrix, i, j, dp));
            }
        }
        return longestPath;
    }

    /**
     * 考虑当前来到 (i,j) 位置，能够走出的最长路径
     *
     * @return 能够走出的最长路径
     */
    private static int process(int[][] matrix, int i, int j, int[][] dp) {
        if (dp[i][j] != 0) {
            return dp[i][j];
        }
        int row = matrix.length;
        int col = matrix[0].length;
        int longestPath = 1;
        // 可能性 1，向左边走
        if (j > 0 && matrix[i][j] < matrix[i][j - 1]) {
            longestPath = Math.max(longestPath, 1 + process(matrix, i, j - 1, dp));
        }
        // 可能性 2，向右边走
        if (j < col - 1 && matrix[i][j] < matrix[i][j + 1]) {
            longestPath = Math.max(longestPath, 1 + process(matrix, i, j + 1, dp));
        }
        // 可能性 3，向上边走
        if (i > 0 && matrix[i][j] < matrix[i - 1][j]) {
            longestPath = Math.max(longestPath, 1 + process(matrix, i - 1, j, dp));
        }
        // 可能性 4，向下边走
        if (i < row - 1 && matrix[i][j] < matrix[i + 1][j]) {
            longestPath = Math.max(longestPath, 1 + process(matrix, i + 1, j, dp));
        }
        dp[i][j] = longestPath;
        return longestPath;
    }

    public static void main(String[] args) {
        int[][] matrix = new int[][]{
                {1, 5, 8},
                {2, 13, 16},
                {7, 9, 0}
        };
        int ans1 = longestPath(matrix);
        int ans2 = longestPath2(matrix);
        System.out.println(ans1);
        System.out.println(ans2);
    }
}
