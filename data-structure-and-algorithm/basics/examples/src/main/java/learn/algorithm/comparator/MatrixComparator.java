package learn.algorithm.comparator;

/**
 * 矩阵（二维数组）对数器
 */
public class MatrixComparator {

    /**
     * 生成一 row*col 的随机矩阵，矩阵元素的值为 0~100 之间的随机数
     *
     * @return 矩阵元素的值为 0~100 之间的随机数
     */
    public static int[][] generateRandomMatrix(int row, int col) {
        int[][] result = new int[row][col];
        for (int i = 0; i != result.length; i++) {
            for (int j = 0; j != result[0].length; j++) {
                result[i][j] = (int) (Math.random() * 100);
            }
        }
        return result;
    }

    /**
     * 生成一 row*col 的随机矩阵，矩阵元素的值不是字符 '1'，就是字符 '0'
     *
     * @return 矩阵元素的值不是字符 '1'，就是字符 '0'
     */
    public static char[][] generateOneOrZeroRandomMatrix(int row, int col) {
        char[][] board = new char[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                board[i][j] = Math.random() < 0.5 ? '1' : '0';
            }
        }
        return board;
    }

    /**
     * 生成一 row*col 的矩阵
     *
     * @return 矩阵元素的值都为 0
     */
    public static int[][] generateZeroMatrix(int row, int col) {
        int[][] board = new int[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                board[i][j] = 0;
            }
        }
        return board;
    }

    /**
     * 生成一个有 0、正、负数矩阵
     *
     * @param row   行数
     * @param col   列数
     * @param value 值得最大范围
     */
    public static int[][] generateRandomMatrix(int row, int col, int value) {
        int[][] arr = new int[row][col];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                arr[i][j] = (int) (Math.random() * value) * (Math.random() > 0.5 ? -1 : 1);
            }
        }
        return arr;
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
