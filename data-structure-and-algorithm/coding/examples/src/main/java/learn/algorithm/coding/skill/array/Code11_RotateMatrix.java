package learn.algorithm.coding.skill.array;

import learn.algorithm.coding.comparator.MatrixComparator;

/**
 * n*n 矩阵原地旋转
 * https://leetcode.cn/problems/rotate-image/
 */
public class Code11_RotateMatrix {

    // 顺时针旋转矩阵 90 度，逐层旋转
    static void rotate1(int[][] matrix) {
        if (matrix == null) {
            return;
        }
        // 从外层到内层一层一层旋转
        int a = 0;
        int b = 0;
        int c = matrix.length - 1;
        int d = matrix[0].length - 1;
        while (a < c) {
            rotateEdge(matrix, a++, b++, c--, d--);
        }
    }

    /**
     * 矩阵边顺时针旋转 90 度
     *
     *    b       d
     * a  o o o o o
     *    o       o
     *    o       o
     *    o       o
     * c  o o o o o
     *
     * @param m 矩阵边
     * @param a 边左上角横坐标
     * @param b 边左上角纵坐标
     * @param c 边右下角横坐标
     * @param d 边右下角纵坐标
     */
    private static void rotateEdge(int[][] m, int a, int b, int c, int d) {
        int rotateTimes = d - b;
        for (int i = 0; i < rotateTimes; i++) {
            int tmp = m[a][b + i];
            m[a][b + i] = m[c - i][b];
            m[c - i][b] = m[c][d - i];
            m[c][d - i] = m[a + i][d];
            m[a + i][d] = tmp;
        }
    }

    // 顺时针旋转矩阵 90 度
    static void rotate2(int[][] matrix) {
        int n = matrix.length;
        // 先沿对角线镜像对称二维矩阵
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
        // 然后反转二维矩阵的每一行
        for (int[] row : matrix) {
            reverse(row);
        }
    }

    // 反转一维数组
    private static void reverse(int[] arr) {
        int i = 0, j = arr.length - 1;
        while (j > i) {
            // swap(arr[i], arr[j]);
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
            i++;
            j--;
        }
    }


    static void rotateCounterclockwise1(int[][] matrix) {
        if (matrix == null) {
            return;
        }
        // 从外层到层一层一层旋转
        int a = 0;
        int b = 0;
        int c = matrix.length - 1;
        int d = matrix[0].length - 1;
        while (a < c) {
            rotateEdgeCounterclockwise(matrix, a++, b++, c--, d--);
        }
    }

    /**
     * 矩阵边逆时针旋转 90 度
     *    b       d
     * a  o o o o o
     *    o       o
     *    o       o
     *    o       o
     * c  o o o o o
     */
    private static void rotateEdgeCounterclockwise(int[][] m, int a, int b, int c, int d) {
        int rotateTimes = d - b;
        for (int i = 0; i < rotateTimes; i++) {
            int tmp = m[a][b + i];
            m[a][b + i] = m[a + i][d];
            m[a + i][d] = m[c][d - i];
            m[c][d - i] = m[c - i][b];
            m[c - i][b] = tmp;
        }
    }

    // 将二维矩阵原地逆时针旋转 90 度
    static void rotateCounterclockwise2(int[][] matrix) {
        int n = matrix.length;
        // 沿逆对角线镜像对称二维矩阵
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - i; j++) {
                // swap(matrix[i][j], matrix[n-j-1][n-i-1])
                int temp = matrix[i][j];
                matrix[i][j] = matrix[n - j - 1][n - i - 1];
                matrix[n - j - 1][n - i - 1] = temp;
            }
        }
        // 然后反转二维矩阵的每一行
        for (int[] row : matrix) {
            reverse(row);
        }
    }

    public static void main(String[] args) {
        int[][] matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
        MatrixComparator.printMatrix(matrix);
        // 顺时针旋转 90
        rotate1(matrix);
        MatrixComparator.printMatrix(matrix);
        // 逆时针针旋转 90
        rotateCounterclockwise1(matrix);
        MatrixComparator.printMatrix(matrix);
        // 顺时针旋转 90
        rotate2(matrix);
        MatrixComparator.printMatrix(matrix);
        // 逆时针针旋转 90
        rotateCounterclockwise2(matrix);
        MatrixComparator.printMatrix(matrix);
    }
}
