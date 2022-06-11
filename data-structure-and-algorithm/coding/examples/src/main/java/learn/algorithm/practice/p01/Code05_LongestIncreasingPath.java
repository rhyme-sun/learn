package learn.algorithm.practice.p01;

/**
 * 题目描述如下：
 * 给定一个二维数组 matrix，你可以从任何位置出发，走向上下左右四个方向，返回能走出来的最长的递增链长度。
 */
public class Code05_LongestIncreasingPath {

    static int longestPath(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return 0;
        }
        int row = matrix.length;
        int col = matrix[0].length;
        int longestPath = 1;
        // 出发点可以是矩阵上的任意一个位置，所以这里枚举所有可能的出发点，并收集最好答案。
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                longestPath = Math.max(longestPath, process(matrix, i, j));
            }
        }
        return longestPath;
    }

    // 考虑当前来到 (i,j) 位置，返回能够走出的最长路径
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

    static int longestPath2(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return 0;
        }
        int row = matrix.length;
        int col = matrix[0].length;
        // dp[i][j] 表示从 (i,j) 位置出发，能够走出的最长升序路径
        int[][] dp = new int[row][col];
        int longestPath = 1;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                longestPath = Math.max(longestPath, process(matrix, i, j, dp));
            }
        }
        return longestPath;
    }

    // 考虑当前来到 (i,j) 位置，返回能够走出的最长路径
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
