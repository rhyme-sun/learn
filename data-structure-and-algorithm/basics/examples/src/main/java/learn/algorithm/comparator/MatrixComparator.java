package learn.algorithm.comparator;

/**
 * 矩阵（二维数组）对数器
 */
public class MatrixComparator {

    /**
     * 生成一 m*n 的随机矩阵，矩阵元素的值为 0~100 之间的随机数
     *
     * @return 矩阵元素的值为 0~100 之间的随机数
     */
    public static int[][] generateRandomMatrix(int m, int n) {
        int[][] result = new int[m][n];
        for (int i = 0; i != result.length; i++) {
            for (int j = 0; j != result[0].length; j++) {
                result[i][j] = (int) (Math.random() * 100);
            }
        }
        return result;
    }

    /**
     * 生成一 m*n 的随机矩阵，矩阵元素的值不是字符 '1'，就是字符 '0'
     *
     * @return 矩阵元素的值不是字符 '1'，就是字符 '0'
     */
    public static char[][] generateOneOrZeroRandomMatrix(int m, int n) {
        char[][] board = new char[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = Math.random() < 0.5 ? '1' : '0';
            }
        }
        return board;
    }

    /**
     * 生成一 m*n 的矩阵
     *
     * @return 矩阵元素的值都为 0
     */
    public static int[][] generateZeroMatrix(int m, int n) {
        int[][] board = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = 0;
            }
        }
        return board;
    }

    /**
     * 矩阵拷贝
     */
    public static char[][] copy(char[][] board) {
        int row = board.length;
        int col = board[0].length;
        char[][] ans = new char[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                ans[i][j] = board[i][j];
            }
        }
        return ans;
    }

    /**
     * 矩阵打印
     */
    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i != matrix.length; i++) {
            for (int j = 0; j != matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
