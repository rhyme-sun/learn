package learn.algorithm.coding.practice.p03;

/**
 * 题目描述如下：
 * 给定一个只有 0 和 1 组成的二维数组，返回边框全是 1 的最大正方形面积。
 * <p>
 * leetcode: https://leetcode.com/problems/largest-1-bordered-square/
 */
public class Code05_Largest1BorderedSquare {

    static int largest1BorderedSquare(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return 0;
        }
        int n = matrix.length;
        int m = matrix[0].length;
        // 求一个点右边最多由几个连续的 1
        int[][] right = right(matrix);
        // 求一个点下边最多由几个连续的 1
        int[][] down = down(matrix);

        // 遍历所有子数组，寻找最优解
        int max = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int sideLen = 1; sideLen <= Math.min(n - i + 1, m - j + 1); sideLen++) {
                    if (right[i][j] >= sideLen && down[i][j] >= sideLen &&
                            right[i + sideLen - 1][j] >= sideLen && down[i][j + sideLen - 1] >= sideLen) {
                        max = Math.max(max, sideLen * sideLen);
                    }
                }
            }
        }
        return max;
    }

    private static int[][] right(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        int[][] right = new int[n][m];

        // 处理最右边界
        for (int i = 0; i < n; i++) {
            right[i][m - 1] = matrix[i][m - 1];
        }
        for (int i = n - 1; i >= 0; i--) {
            for (int j = m - 2; j >= 0; j--) {
                if (matrix[i][j] == 1) {
                    right[i][j] = right[i][j + 1] + 1;
                }
            }
        }
        return right;
    }

    private static int[][] down(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        int[][] down = new int[n][m];
        // 处理最下边界
        for (int j = 0; j < m; j++) {
            down[n-1][j] = matrix[n-1][j];
        }
        for (int j = m - 1; j >= 0; j--) {
            for (int i = n - 2; i >= 0; i--) {
                if (matrix[i][j] == 1) {
                    down[i][j] = down[i + 1][j] + 1;
                }
            }
        }
        return down;
    }


    public static void main(String[] args) {
        int[][] matrix = {{1, 1, 1}, {1, 1, 1}, {1, 0, 1}};
        //int[][] right = right(matrix);
        //int[][] down = down(matrix);
        //ArrayComparator.printArray(right);
        //ArrayComparator.printArray(down);
        int ans = largest1BorderedSquare(matrix);
        System.out.println(ans);
    }
}
