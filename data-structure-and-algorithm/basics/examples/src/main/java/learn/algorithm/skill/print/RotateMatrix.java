package learn.algorithm.skill.print;

import learn.algorithm.comparator.MatrixComparator;

/**
 * n*n 矩阵原地旋转
 */
public class RotateMatrix {

    static void rotateMatrixClockwise(int[][] matrix) {
        if (matrix == null) {
            return;
        }
        // 从外层到内层一层一层旋转
        int a = 0;
        int b = 0;
        int c = matrix.length - 1;
        int d = matrix[0].length - 1;
        while (a < c) {
            rotateEdgeClockwise(matrix, a++, b++, c--, d--);
        }
    }

    /**
     * 矩阵边顺时针旋转 90 度
     *
     * @param m 矩阵边
     * @param a 边左上角横坐标
     * @param b 边左上角纵坐标
     * @param c 边右下角横坐标
     * @param d 边右下角纵坐标
     */
    private static void rotateEdgeClockwise(int[][] m, int a, int b, int c, int d) {
        int rotateTimes = d - b;
        for (int i = 0; i < rotateTimes; i++) {
            int tmp = m[a][b + i];
            m[a][b + i] = m[c - i][b];
            m[c - i][b] = m[c][d - i];
            m[c][d - i] = m[a + i][d];
            m[a + i][d] = tmp;
        }
    }

    static void rotateMatrixCounterclockwise(int[][] matrix) {
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

    public static void main(String[] args) {
        int[][] matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
        MatrixComparator.printMatrix(matrix);
        rotateMatrixClockwise(matrix);
        MatrixComparator.printMatrix(matrix);
        rotateMatrixCounterclockwise(matrix);
        MatrixComparator.printMatrix(matrix);
    }
}
